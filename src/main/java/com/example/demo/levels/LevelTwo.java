package com.example.demo.levels;

import com.example.demo.actors.Boss;
import com.example.demo.levels.banners.LevelTwoBanner;
import com.example.demo.levels.views.LevelView;
import com.example.demo.levels.views.LevelViewLevelTwo;
import com.example.demo.ui.FullScreenHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static com.example.demo.controller.Main.getScreenHeight;

/**
 * Represents the second level of the game.
 * This level includes a boss enemy with a shield and transitions to Level Three upon completion.
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;

	private final Boss boss; // The boss enemy for this level
	private LevelViewLevelTwo levelView; // Custom level view for Level Two
	private boolean levelTransitioned = false; // Flag to track if the level has transitioned
	private boolean bannerDisplayed = false; // Flag to track if the banner has been displayed

	/**
	 * Constructs a new LevelTwo instance.
	 *
	 * @param screenHeight the height of the game screen
	 * @param screenWidth  the width of the game screen
	 * @param stage        the primary stage for the level
	 */
	public LevelTwo(double screenHeight, double screenWidth, Stage stage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
		boss = new Boss();
	}

	/**
	 * Initializes the friendly units for the level, including the user plane and the boss's shield.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		// Add the boss's shield to the root
		getRoot().getChildren().add(boss.getShieldImage());
	}

	/**
	 * Initializes the scene for Level Two, including the background, user plane, and banner.
	 *
	 * @return the initialized scene for Level Two
	 */
	@Override
	public Scene initializeScene() {
		Scene scene = super.initializeScene();
		Group root = getRoot();

		// Display the Level Two banner only if it hasn't been displayed before
		if (!bannerDisplayed) {
			LevelTwoBanner levelTwoBanner = new LevelTwoBanner(FullScreenHandler.SCREEN_WIDTH, FullScreenHandler.SCREEN_HEIGHT);
			levelTwoBanner.addTo(getRoot());
			bannerDisplayed = true; // Set the flag to true after showing the banner
		}

		return scene;
	}

	/**
	 * Checks if the game is over due to the player's destruction or if the boss has been defeated.
	 * If the boss is defeated, transitions to Level Three.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed() && !levelTransitioned) {
			levelTransitioned = true;

			System.out.println("change to level 3");
			try {
				LevelParent nextLevel = new LevelThree(getScreenHeight(), getScreenWidth(), getStage());
				getStage().setScene(nextLevel.initializeScene());
				nextLevel.startGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Spawns enemy units for the level. In this level, the boss is added as the sole enemy.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Instantiates the custom level view for Level Two.
	 * Adds the boss's shield to the view and displays it.
	 *
	 * @return the LevelView instance for Level Two
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		levelView.showShield();
		return levelView;
	}
}
