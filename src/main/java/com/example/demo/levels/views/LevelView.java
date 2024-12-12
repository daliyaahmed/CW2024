package com.example.demo.levels.views;

import com.example.demo.ui.GameOverImage;
import com.example.demo.ui.HeartDisplay;
import com.example.demo.ui.WinImage;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The {@code LevelView} class is responsible for managing and displaying various
 * UI components during a game level, such as heart displays, win and game-over images,
 * and a kill count display.
 */
public class LevelView {
	/**
	 * The X-coordinate position for displaying the heart display.
	 */
	private static final double HEART_DISPLAY_X_POSITION = 5;

	/**
	 * The Y-coordinate position for displaying the heart display.
	 */
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	/**
	 * The X-coordinate position for displaying the win image.
	 */
	private static final int WIN_IMAGE_X_POSITION = 355;

	/**
	 * The Y-coordinate position for displaying the win image.
	 */
	private static final int WIN_IMAGE_Y_POSITION = 175;

	/**
	 * The X-coordinate position for displaying the game-over image.
	 */
	private static final int LOSS_SCREEN_X_POSITION = 50;

	/**
	 * The Y-coordinate position for displaying the game-over image.
	 */
	private static final int LOSS_SCREEN_Y_POSITION = -400;

	/**
	 * The width of the game screen.
	 */
	private static final int SCREEN_WIDTH = 1500;


	/** The root group to which UI elements are added. */
	public final Group root;

	/** Image displayed when the player wins. */
	private final WinImage winImage;

	/** Image displayed when the player loses. */
	private final GameOverImage gameOverImage;

	/** Display for the player's remaining hearts. */
	private final HeartDisplay heartDisplay;

	/** Display for the kill count. */
	private final Text killCount;

	/**
	 * Constructs a {@code LevelView} object with the specified root group, number of hearts to display,
	 * and whether to show the kill count.
	 *
	 * @param root the root {@code Group} to which UI elements are added
	 * @param heartsToDisplay the initial number of hearts to display
	 * @param showKillCount {@code true} to display the kill count, {@code false} otherwise
	 */
	public LevelView(Group root, int heartsToDisplay, boolean showKillCount) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);

		if (showKillCount) {
			this.killCount = new Text("Kill Count: 0");
			killCount.setStyle("-fx-font-size: 20px; -fx-fill: white; -fx-font-weight: bold;");
			root.getChildren().add(killCount);
			updateKillCountPos();
		} else {
			this.killCount = null;
		}
	}

	/**
	 * Displays the heart display on the screen.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image on the screen.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	public Group getRoot() {
		return root;
	}
	/**
	 * Displays the game-over image on the screen.
	 */
	public void showGameOverImage() {
		if (!root.getChildren().contains(gameOverImage)) {
			root.getChildren().add(gameOverImage);
		}
	}

	/**
	 * Updates the heart display to match the number of hearts remaining.
	 *
	 * @param heartsRemaining the number of hearts remaining
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Updates the position and styling of the kill count display.
	 */
	public void updateKillCountPos() {
		if (killCount != null) {
			killCount.applyCss();
			killCount.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 40));
			killCount.setStyle("-fx-font-size: 30px;" +
					"-fx-text-fill: linear-gradient(#8B008B, #00008B);" +
					"-fx-font-weight: bold;");

			DropShadow neonGlow = new DropShadow();
			neonGlow.setColor(Color.GOLD);
			neonGlow.setRadius(20);
			neonGlow.setSpread(0.4);
			killCount.setEffect(neonGlow);

			double xPosition = (SCREEN_WIDTH - killCount.getBoundsInLocal().getWidth()) / 2;
			double yPosition = 60;

			killCount.setX(xPosition);
			killCount.setY(yPosition);
		}
	}

	/**
	 * Updates the displayed kill count to the specified value.
	 *
	 * @param newKillCount the updated kill count
	 */
	public void updateKillCount(int newKillCount) {
		if (killCount != null) {
			killCount.setText("Kill Count: " + newKillCount);
			killCount.toFront();
			updateKillCountPos();
		}
	}
}
