package com.example.demo.projectiles;

/**
 * The {@code BossProjectile} class represents a projectile fired by the boss character in the game.
 * It extends the {@code Projectile} class and defines specific behavior, including its image, size,
 * initial position, and movement logic.
 */
public class BossProjectile extends Projectile {

	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a {@code BossProjectile} at the specified initial Y-coordinate.
	 * The X-coordinate is fixed to {@code INITIAL_X_POSITION}.
	 *
	 * @param initialYPos the initial Y-coordinate of the projectile
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 * The movement is determined by a fixed horizontal velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the projectile. This method calls {@link #updatePosition()}
	 * to update the projectile's position based on its velocity.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
