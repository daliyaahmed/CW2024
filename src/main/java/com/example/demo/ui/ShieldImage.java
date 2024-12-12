package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code ShieldImage} class represents a shield image that can be displayed,
 * hidden, or updated in position during gameplay. It tracks the state of the shield
 * (on or off) and provides methods to manage its visibility and position.
 */
public class ShieldImage extends ImageView {

	/** The size (height and width) of the shield image. */
	private static final int SHIELD_SIZE = 200;

	/** Tracks whether the shield is currently active or not. */
	private boolean isShieldOn = false;

	/**
	 * Constructs a {@code ShieldImage} at the specified position.
	 *
	 * @param xPosition the X coordinate where the shield should be placed
	 * @param yPosition the Y coordinate where the shield should be placed
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));
		this.setVisible(true);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Displays the shield if it is not already on.
	 */
	public void showShield() {
		if (!isShieldOn) {
			isShieldOn = true;
			this.setVisible(true); // Show shield
		}
	}

	/**
	 * Hides the shield from view.
	 */
	public void hideShield() {
		this.setVisible(false); // Hide shield
	}

	/**
	 * Updates the position of the shield to follow the object it is protecting.
	 *
	 * @param xPosition the new X coordinate for the shield
	 * @param yPosition the new Y coordinate for the shield
	 */
	public void updatePosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}
}
