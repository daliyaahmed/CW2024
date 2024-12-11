package com.example.demo.levels;


import com.example.demo.actors.l3enemies.PafPlanePool;
import com.example.demo.actors.l3enemies.PafPlane;
import com.example.demo.levels.banners.LevelThreeBanner;
import com.example.demo.levels.views.LevelView;
import com.example.demo.ui.PowerUpManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.controller.Main.getScreenHeight;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level3background.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.levels.LevelFour";
    private static final int TOTAL_ENEMIES = 10;
    private static final int KILLS_TO_ADVANCE = 5;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private PowerUpManager powerUpManager;
    private static final double OFF_SCREEN_THRESHOLD = -50; // Beyond the left edge
    // Object pool for PafPlane enemies
    private PafPlanePool planePool;
    private final List<PafPlane> enemies = new ArrayList<>();

    // Flag to track if the level has already transitioned
    private boolean levelTransitioned = false;
    private boolean bannerDisplayed = false;
    // Flag to track if the banner has been displayed
    //private boolean bannerDisplayed = false;
    public LevelThree(double screenHeight, double screenWidth,  Stage stage) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
        this.planePool = new PafPlanePool(screenWidth);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget() && !levelTransitioned) {
            levelTransitioned = true;
            //winGame();

            System.out.println("change to level 4");
            try {
                LevelParent nextLevel = new LevelFour(getScreenHeight(), getScreenWidth(), getStage());
                getStage().setScene(nextLevel.initializeScene());
                nextLevel.startGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        Group root = getRoot();

        // Initialize PowerUpManager
        powerUpManager = new PowerUpManager(root);

        // Add PowerUp button click handler
        powerUpManager.getPowerUpButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Check if power-ups are available
            if (powerUpManager.hasPowerUpsRemaining()) {
                // Activate PowerUp for the user plane
                getUser().activatePowerUp();

                // Activate PowerUp in PowerUpManager
                powerUpManager.activatePowerUp();
            }
        });

        // Display the Level One banner only if it hasn't been displayed before
        if (!bannerDisplayed) {
            LevelThreeBanner levelThreeBanner = new LevelThreeBanner(getScreenWidth(), getScreenHeight());
            levelThreeBanner.addTo(root);
            bannerDisplayed = true; // Set the flag to true after showing the banner
        }

        return scene;
    }
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY ) {

                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                PafPlane newEnemy = planePool.getPlane(newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
                //getRoot().getChildren().add(newEnemy);
               // double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
               // ActiveActorDestructible newPafEnemy = new PafPlane(getScreenWidth(), newEnemyInitialYPosition);
               // addEnemyUnit(newPafEnemy);
            }
        }
        // Remove destroyed enemies and return them to the pool
        List<PafPlane> destroyedEnemies = new ArrayList<>();
        for (PafPlane enemy : enemies) {
            if (enemy.isDestroyed()) {
                destroyedEnemies.add(enemy);
                getRoot().getChildren().remove(enemy);
                planePool.releasePlane(enemy);
            }
        }
        enemies.removeAll(destroyedEnemies);

    }

        @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, true);
    }
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
    public void goToNextLevel(String nextLevel) {
        super.goToNextLevel(nextLevel); // Call the parent class method for the transition
    }

}
