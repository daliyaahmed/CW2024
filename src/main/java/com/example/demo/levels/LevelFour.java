package com.example.demo.levels;


import com.example.demo.actors.l4enemies.L4EnemyFactory;
import com.example.demo.levels.banners.LevelThreeBanner;
import com.example.demo.levels.views.LevelView;
import com.example.demo.levels.views.LevelViewLevelFour;
import com.example.demo.ui.FullScreenHandler;
import com.example.demo.ui.PowerUpManager;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.demo.controller.Main.getScreenHeight;

public class LevelFour extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/l4background.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 10;
    private static final int LEVEL_TIME_LIMIT = 60;

    private final L4EnemyFactory enemyFactory;
    private PowerUpManager powerUpManager;
    private int remainingTime;
    private Timeline countdownTimer;
    private boolean bannerDisplayed = false;
    public LevelFour(double screenHeight, double screenWidth, Stage stage) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
        this.enemyFactory = new L4EnemyFactory(getUser(),              // Reference to the user plane
                userProjectiles,        // List of user projectiles (inherited from LevelParent)
                enemyUnits,             // List of active enemies (inherited from LevelParent)
                enemyProjectiles,       // List of enemy projectiles (inherited from LevelParent)
                getRoot()               // Root group for the game scene

                 );

        this.remainingTime = LEVEL_TIME_LIMIT;
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
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
        startLevelCountdown(); // Start the countdown timer
        return scene;
    }

    @Override
    protected void spawnEnemyUnits() {
        enemyFactory.spawnEnemies(FullScreenHandler.SCREEN_WIDTH, FullScreenHandler.SCREEN_HEIGHT);
    }
    @Override
    public void updateScene() {
        super.updateScene();
        enemyFactory.spawnEnemies(FullScreenHandler.SCREEN_WIDTH, FullScreenHandler.SCREEN_HEIGHT);
        enemyFactory.handleCollisions();

    }
    private void startLevelCountdown() {
        LevelViewLevelFour view = (LevelViewLevelFour) getLevelView();
        view.startCountdownTimer(() -> {
            loseGame(); // Trigger game-over logic when the timer reaches zero
        });
    }

    @Override
    protected void checkIfGameOver() {
        // Check if the level is completed
        if (enemyFactory.isLevelComplete()) {
            winGame();
        }

        // Check if the user is destroyed or time has run out
        if (userIsDestroyed() || remainingTime <= 0) {
            loseGame();
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH);
    }





}
