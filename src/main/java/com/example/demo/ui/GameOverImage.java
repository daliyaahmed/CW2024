package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;

/**
 * The {@code GameOverImage} class represents the "Game Over" image displayed in the game.
 * It extends {@link ImageView} and provides customization for its position and scaling.
 */
public class GameOverImage extends ImageView {

	/** The file path to the "Game Over" image. */
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Constructs a {@code GameOverImage} and sets its position and scaling.
	 *
	 * @param xPosition the X-coordinate position of the image
	 * @param yPosition the Y-coordinate position of the image
	 */
	public GameOverImage(double xPosition, double yPosition) {
		// Load the "Game Over" image
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		// Set the layout positions for the image
		setLayoutX(450);
		setLayoutY(50);
		// Scale down the image to 50% of its original size
		Scale scale = new Scale(0.5, 0.5);
		getTransforms().add(scale);
	}
}
