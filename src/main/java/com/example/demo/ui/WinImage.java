package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code WinImage} class represents an image that is displayed when the player wins a level.
 * This class provides methods to configure and show the "You Win" image.
 */
public class WinImage extends ImageView {

	/** The file path for the "You Win" image. */
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";

	/** The height of the "You Win" image. */
	private static final int HEIGHT = 500;

	/** The width of the "You Win" image. */
	private static final int WIDTH = 600;

	/**
	 * Constructs a {@code WinImage} at the specified position.
	 *
	 * @param xPosition the X coordinate where the "You Win" image should be placed
	 * @param yPosition the Y coordinate where the "You Win" image should be placed
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false); // Initially hidden
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(500);
		this.setLayoutY(200);
	}

	/**
	 * Makes the "You Win" image visible on the screen.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}
}
