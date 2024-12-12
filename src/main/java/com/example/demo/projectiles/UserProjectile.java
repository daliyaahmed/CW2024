package com.example.demo.projectiles;

import com.example.demo.projectiles.Projectile;

/**
 * The {@code UserProjectile} class represents a projectile fired by the user.
 * It extends the {@code Projectile} class and defines specific behavior for user-fired projectiles,
 * including movement and updating its position.
 */
public class UserProjectile extends Projectile {

	/** The name of the image file representing the user's projectile. */
	private static final String IMAGE_NAME = "userfire.png";

	/** The height of the projectile's image for scaling purposes. */
	private static final int IMAGE_HEIGHT = 10;

	/** The horizontal velocity of the user's projectile. */
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a {@code UserProjectile} with the specified initial position.
	 *
	 * @param initialXPos the initial X-coordinate of the projectile
	 * @param initialYPos the initial Y-coordinate of the projectile
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the user's projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the projectile. This method calls {@link #updatePosition()}
	 * to update the projectile's location.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
