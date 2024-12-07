package com.example.demo.levels;

import com.example.demo.actors.Boss;
import javafx.stage.Stage;



public class LevelTwo extends LevelParent {

	private static final String LEVEL_THREE_CLASS_NAME = "com.example.demo.levels.LevelThree";
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;
	private boolean levelTransitioned = false;
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
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed() && !levelTransitioned) {
			levelTransitioned = true;

			System.out.println("change to level 3");
			try {

				goToNextLevel(LEVEL_THREE_CLASS_NAME);
			} catch (Exception e) {
				e.printStackTrace();  // This will help identify the root cause of the error
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
