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


public class LevelTwo extends LevelParent {

	private static final String LEVEL_THREE_CLASS_NAME = "com.example.demo.levels.LevelThree";
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;
	private boolean levelTransitioned = false;
	private boolean bannerDisplayed = false;
	public LevelTwo(double screenHeight, double screenWidth,  Stage stage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage);
		boss = new Boss();

	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		// Add shield to the root
		getRoot().getChildren().add(boss.getShieldImage());
	}
	@Override
	public Scene initializeScene() {
		// Call the parent initializeScene to retain basic setup (background, friendly units, etc.)
		Scene scene = super.initializeScene();
		Group root = getRoot();
		// Display the Level One banner only if it hasn't been displayed before
		if (!bannerDisplayed) {
			LevelTwoBanner levelTwoBanner = new LevelTwoBanner(FullScreenHandler.SCREEN_WIDTH, FullScreenHandler.SCREEN_HEIGHT);
			levelTwoBanner.addTo(getRoot());
			bannerDisplayed = true; // Set the flag to true after showing the banner
		}
		return scene;
	}
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed() && !levelTransitioned) {
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

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		levelView.showShield();
		return levelView;
	}
}
