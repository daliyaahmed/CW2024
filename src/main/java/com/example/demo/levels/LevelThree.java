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

/**
 * Represents the third level of the game.
 * Includes logic for enemy spawning, power-ups, and transitioning to the next level.
 */
public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/level3background.jpg";
    private static final int TOTAL_ENEMIES = 10;
    private static final int KILLS_TO_ADVANCE = 15;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    private PowerUpManager powerUpManager;
    private final PafPlanePool planePool; // Object pool for PafPlane enemies
    private final List<PafPlane> enemies = new ArrayList<>();
    private boolean levelTransitioned = false; // Flag to track level transition
    private boolean bannerDisplayed = false;  // Flag to track if the banner has been displayed

    /**
     * Constructs a new LevelThree instance.
     *
     * @param screenHeight the height of the game screen
     * @param screenWidth  the width of the game screen
     * @param stage        the primary stage for the level
     */
    public LevelThree(double screenHeight, double screenWidth, Stage stage) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
        this.planePool = new PafPlanePool(screenWidth);
    }

    /**
     * Checks if the game is over due to player destruction or if the kill target has been reached.
     * If the kill target is reached, transitions to Level Four.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget() && !levelTransitioned) {
            levelTransitioned = true;
            System.out.println("change to level 4");
            try {
                // Clear all actors
                enemyUnits.clear();
                enemyProjectiles.clear();
                userProjectiles.clear();
                LevelParent nextLevel = new LevelFour(getScreenHeight(), getScreenWidth(), getStage());
                getStage().setScene(nextLevel.initializeScene());
                nextLevel.startGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes the scene for Level Three, including background, user plane, and power-up button.
     *
     * @return the initialized scene for Level Three
     */
    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        Group root = getRoot();

        // Initialize PowerUpManager
        powerUpManager = new PowerUpManager(root);

        // Add PowerUp button click handler
        powerUpManager.getPowerUpButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (powerUpManager.hasPowerUpsRemaining()) {
                getUser().activatePowerUp();
                powerUpManager.activatePowerUp();
            }
        });

        // Display the banner only if it hasn't been displayed before
        if (!bannerDisplayed) {
            LevelThreeBanner levelThreeBanner = new LevelThreeBanner(getScreenWidth(), getScreenHeight());
            levelThreeBanner.addTo(root);
            bannerDisplayed = true;
        }

        return scene;
    }

    /**
     * Adds the user plane to the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns enemy units in the level using an object pool for better memory management.
     * Removes destroyed enemies and returns them to the pool.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                PafPlane newEnemy = planePool.getPlane(newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
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

    /**
     * Instantiates the LevelView for Level Three.
     *
     * @return the LevelView instance for Level Three
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, true);
    }

    /**
     * Checks if the user has reached the required kill count to advance to the next level.
     *
     * @return true if the kill count has been reached, false otherwise
     */
    public boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Transitions to the next level by firing a property change event.
     *
     * @param nextLevel the name of the next level to transition to
     */
    public void goToNextLevel(String nextLevel) {
        super.goToNextLevel(nextLevel);
    }
}
