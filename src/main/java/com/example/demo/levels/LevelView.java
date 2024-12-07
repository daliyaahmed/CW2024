package com.example.demo.levels;

import com.example.demo.ui.GameOverImage;
import com.example.demo.ui.HeartDisplay;
import com.example.demo.ui.WinImage;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = 50;
	private static final int LOSS_SCREEN_Y_POSITION = -400;
	private static final int SCREEN_WIDTH = 1500;
	public final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final Text killCount;


	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		//kill count
		this.killCount = new Text("Kill Count: 0");
		killCount.setStyle("-fx-font-size: 20px; -fx-fill: white; -fx-font-weight: bold;");
		root.getChildren().add(killCount); // Add kill count text to the root

		updateKillCountPos();
	}



	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	public void showGameOverImage() {if (!root.getChildren().contains(gameOverImage)) { // Check if image is already in the scene
			root.getChildren().add(gameOverImage);
		}
	}

	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}
	public void updateKillCountPos() {
		// Apply CSS to ensure we get an accurate size after any style changes
		killCount.applyCss();

		// Set the gradient text fill using CSS styling
		killCount.setStyle(
						"-fx-font-family: 'Lucida Console';" +  // Font family
						"-fx-font-size: 30px;" +       // Font size
						"-fx-text-fill: linear-gradient(#8B008B, #00008B);" + // Gradient from dark pink to dark blue
						"-fx-font-weight: bold;"       // Bold font for emphasis
		);
		// Add a neon-like glow effect using a DropShadow
		DropShadow neonGlow = new DropShadow();
		neonGlow.setColor(Color.GOLD);
		neonGlow.setRadius(20);
		neonGlow.setSpread(0.4); // Makes the glow more intense
		killCount.setEffect(neonGlow);


		// Set X and Y positions to place the text in the top right corner
		double xPosition = (SCREEN_WIDTH - killCount.getBoundsInLocal().getWidth()) / 2; // A small margin (20) from the right edge
		double yPosition = 60; // A small margin (20) from the top edge

		// Set the position of the kill count
		killCount.setX(xPosition);
		killCount.setY(yPosition);
	}
	// Method to update the kill count display
	public void updateKillCount(int newKillCount) {
		killCount.setText("Kill Count: " + newKillCount);
		killCount.toFront();
		updateKillCountPos();
	}

}
