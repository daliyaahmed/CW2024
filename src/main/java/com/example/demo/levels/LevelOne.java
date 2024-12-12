package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.l1EnemyPlane;
import com.example.demo.levels.banners.LevelOneBanner;
import com.example.demo.levels.views.LevelView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.example.demo.controller.Main.getScreenHeight;

/**
 * The {@code LevelOne} class represents the first level of the game.
 * It manages the spawning of enemies, checks game-over conditions, and transitions to the next level.
 */
public class LevelOne extends LevelParent {

	/**
	 * Path to the background image for Level One.
	 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";

	/**
	 * Total number of enemies to spawn in Level One.
	 */
	private static final int TOTAL_ENEMIES = 5;

	/**
	 * Number of kills required to advance to the next level.
	 */
	private static final int KILLS_TO_ADVANCE = 10;

	/**
	 * Probability of spawning an enemy in each frame.
	 */
	private static final double ENEMY_SPAWN_PROBABILITY = .20;

	/**
	 * Initial health of the player's plane in Level One.
	 */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * Flag to track if the level has already transitioned.
	 */
	private boolean levelTransitioned = false;

	/**
	 * Flag to track if the level banner has been displayed.
	 */
	private boolean bannerDisplayed = false;

	/**
	 * Constructs a new {@code LevelOne} instance.
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth  the width of the screen
	 * @param stage        the primary stage of the application
	 */
	public LevelOne(double screenHeight, double screenWidth, Stage stage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
	}

	/**
	 * Checks if the game is over based on user destruction or reaching the kill target.
	 * Transitions to the next level if the user meets the kill target.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget() && !levelTransitioned) {
			levelTransitioned = true;

			System.out.println("Changing to Level 2");
			try {
				LevelParent nextLevel = new LevelTwo(getScreenHeight(), getScreenWidth(), getStage());
				getStage().setScene(nextLevel.initializeScene());
				nextLevel.startGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initializes the scene for Level One, including the banner display.
	 *
	 * @return the initialized scene
	 */
	@Override
	public Scene initializeScene() {
		Scene scene = super.initializeScene();
		Group root = getRoot();

		if (!bannerDisplayed) {
			LevelOneBanner levelOneBanner = new LevelOneBanner(getScreenWidth(), getScreenHeight());
			levelOneBanner.addTo(root);
			bannerDisplayed = true;
		}

		return scene;
	}

	/**
	 * Initializes friendly units (e.g., user plane) for Level One.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy units in Level One based on the spawn probability.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new l1EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Instantiates the {@link LevelView} specific to Level One.
	 *
	 * @return the initialized {@link LevelView} instance
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, true);
	}

	/**
	 * Checks if the user has reached the kill target required to advance to the next level.
	 *
	 * @return {@code true} if the user has reached the kill target, {@code false} otherwise
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}



	/**
	 * Transitions to the next level.
	 *
	 * @param nextLevel the name of the next level to transition to
	 */
	public void goToNextLevel(String nextLevel) {
		super.goToNextLevel(nextLevel);
	}
}
