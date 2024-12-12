package com.example.demo.levels;

import com.example.demo.actors.l4enemies.L4EnemyFactory;
import com.example.demo.levels.banners.LevelFourBanner;
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

/**
 * The {@code LevelFour} class defines the behavior and elements of Level Four in the game.
 * It includes logic for spawning enemies, managing power-ups, and checking game-over conditions.
 */
public class LevelFour extends LevelParent {

    /**
     * Path to the background image for Level Four.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/LevelFourB.png";

    /**
     * Initial health of the player's plane.
     */
    private static final int PLAYER_INITIAL_HEALTH = 10;

    /**
     * Time limit for the level in seconds.
     */
    private static final int LEVEL_TIME_LIMIT = 60;

    /**
     * Factory for creating enemy units in Level Four.
     */
    private final L4EnemyFactory enemyFactory;

    /**
     * Manager for handling power-ups in the level.
     */
    private PowerUpManager powerUpManager;

    /**
     * Remaining time for the level.
     */
    private int remainingTime;

    /**
     * Countdown timer for the level.
     */
    private Timeline countdownTimer;

    /**
     * Flag indicating whether the level banner has been displayed.
     */
    private boolean bannerDisplayed = false;

    /**
     * Constructs a new {@code LevelFour} instance.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth  the width of the screen
     * @param stage        the primary stage of the application
     */
    public LevelFour(double screenHeight, double screenWidth, Stage stage) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
        this.enemyFactory = new L4EnemyFactory(
                getUser(),              // Reference to the user plane
                userProjectiles,        // List of user projectiles (inherited from LevelParent)
                enemyUnits,             // List of active enemies (inherited from LevelParent)
                enemyProjectiles,       // List of enemy projectiles (inherited from LevelParent)
                getRoot()               // Root group for the game scene
        );
        this.remainingTime = LEVEL_TIME_LIMIT;
    }

    /**
     * Initializes friendly units in the level.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }
    public L4EnemyFactory getEnemyFactory() {
        return enemyFactory;
    }

    /**
     * Initializes the scene for Level Four.
     *
     * @return the initialized scene
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

        // Display the Level Four banner if not already displayed
        if (!bannerDisplayed) {
            LevelFourBanner levelFourBanner = new LevelFourBanner(getScreenWidth(), getScreenHeight());
            levelFourBanner.addTo(root);
            bannerDisplayed = true;
        }

        startLevelCountdown(); // Start the countdown timer
        return scene;
    }

    /**
     * Spawns enemy units in Level Four.
     */
    @Override
    protected void spawnEnemyUnits() {
        enemyFactory.spawnEnemies(FullScreenHandler.SCREEN_WIDTH, FullScreenHandler.SCREEN_HEIGHT);
    }

    /**
     * Updates the scene by spawning enemies and handling collisions.
     */
    @Override
    public void updateScene() {
        super.updateScene();
        enemyFactory.spawnEnemies(FullScreenHandler.SCREEN_WIDTH, FullScreenHandler.SCREEN_HEIGHT);
        enemyFactory.handleCollisions();
    }

    /**
     * Starts the countdown timer for Level Four.
     */
    private void startLevelCountdown() {
        LevelViewLevelFour view = (LevelViewLevelFour) getLevelView();
        view.startCountdownTimer(this::loseGame);
    }

    /**
     * Checks if the game is over based on conditions such as level completion, user destruction, or time running out.
     */
    @Override
    protected void checkIfGameOver() {
        if (enemyFactory.isLevelComplete()) {
            winGame();
        }
        if (userIsDestroyed() || remainingTime <= 0) {
            loseGame();
        }
    }

    /**
     * Instantiates the {@link LevelView} specific to Level Four.
     *
     * @return the initialized {@link LevelViewLevelFour} instance
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH);
    }
}
