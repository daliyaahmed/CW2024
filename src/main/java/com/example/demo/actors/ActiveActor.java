package com.example.demo.actors;

import javafx.scene.image.*;

/**
 * The {@code ActiveActor} class is an abstract base class representing any active actor in the game.
 * It provides common functionality for positioning and movement, and requires subclasses to implement
 * the {@code updatePosition} method.
 */
public abstract class ActiveActor extends ImageView {

	/**
	 * The location of the image resources for the actors.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an {@code ActiveActor} with the specified image, dimensions, and initial position.
	 *
	 * @param imageName the name of the image file to represent the actor
	 * @param imageHeight the height of the actor's image
	 * @param initialXPos the initial X-coordinate of the actor
	 * @param initialYPos the initial Y-coordinate of the actor
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor. Subclasses must implement this method to define
	 * how the actor's position changes over time.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by a specified amount.
	 *
	 * @param horizontalMove the amount to move the actor along the X-axis
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified amount.
	 *
	 * @param verticalMove the amount to move the actor along the Y-axis
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
