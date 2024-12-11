package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.l1EnemyPlane;
import com.example.demo.levels.banners.LevelOneBanner;
import com.example.demo.levels.views.LevelView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.example.demo.controller.Main.getScreenHeight;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.levels.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 5;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	// Flag to track if the level has already transitioned
	private boolean levelTransitioned = false;
	// Flag to track if the banner has been displayed
	private boolean bannerDisplayed = false;
	public LevelOne(double screenHeight, double screenWidth,  Stage stage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);

	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget() && !levelTransitioned) {
			levelTransitioned = true;

			System.out.println("change to level 2");
			try {
				LevelParent nextLevel = new LevelTwo(getScreenHeight(), getScreenWidth(), getStage());
				getStage().setScene(nextLevel.initializeScene());
				nextLevel.startGame();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	@Override
	public Scene initializeScene() {
		// Call the parent initializeScene to retain basic setup (background, friendly units, etc.)
		Scene scene = super.initializeScene();
		Group root = getRoot();

		// Display the Level One banner only if it hasn't been displayed before
		if (!bannerDisplayed) {
			LevelOneBanner levelOneBanner = new LevelOneBanner(getScreenWidth(), getScreenHeight());
			levelOneBanner.addTo(root);
			bannerDisplayed = true; // Set the flag to true after showing the banner
		}

		// Return the initialized scene
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
				ActiveActorDestructible newEnemy = new l1EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
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
