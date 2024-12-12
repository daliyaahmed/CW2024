package com.example.demo.actors;

import com.example.demo.projectiles.EnemyProjectile;

/**
 * The {@code l1EnemyPlane} class represents a level one enemy plane in the game.
 * This plane moves horizontally and fires projectiles at a specified rate.
 */
public class l1EnemyPlane extends FighterPlane {

	/**
	 * The name of the image file representing the plane.
	 */
	private static final String IMAGE_NAME = "enemyplane.png";

	/**
	 * The height of the plane's image.
	 */
	private static final int IMAGE_HEIGHT = 50;

	/**
	 * The horizontal velocity of the plane.
	 */
	private static final int HORIZONTAL_VELOCITY = -6;

	/**
	 * The X-offset for the projectile's position.
	 */
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;

	/**
	 * The Y-offset for the projectile's position.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;

	/**
	 * The initial health of the plane.
	 */
	private static final int INITIAL_HEALTH = 1;

	/**
	 * The rate at which the plane fires projectiles.
	 */
	private static final double FIRE_RATE = .01;

	/**
	 * Constructs an {@code l1EnemyPlane} with the specified initial position.
	 *
	 * @param initialXPos the initial X-coordinate of the plane
	 * @param initialYPos the initial Y-coordinate of the plane
	 */
	public l1EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the plane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile from the plane if the random chance is less than the firing rate.
	 *
	 * @return an instance of {@code EnemyProjectile} if the plane fires; otherwise, {@code null}
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	/**
	 * Updates the state of the plane, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
