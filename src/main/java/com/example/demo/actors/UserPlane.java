package com.example.demo.actors;

import com.example.demo.projectiles.UserProjectile;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * The {@code UserPlane} class represents the player's plane in the game.
 * It allows the player to move vertically, fire projectiles, and activate power-ups.
 */
public class UserPlane extends FighterPlane {

	/**
	 * The name of the image file representing the user plane.
	 */
	private static final String IMAGE_NAME = "userplane.png";

	/**
	 * The upper Y-coordinate bound for the plane's movement.
	 */
	private static final double Y_UPPER_BOUND = -40;

	/**
	 * The lower Y-coordinate bound for the plane's movement.
	 */
	private static final double Y_LOWER_BOUND = 600.0;

	/**
	 * The initial X-coordinate of the user plane.
	 */
	private static final double INITIAL_X_POSITION = 5.0;

	/**
	 * The initial Y-coordinate of the user plane.
	 */
	private static final double INITIAL_Y_POSITION = 300.0;

	/**
	 * The height of the user plane's image.
	 */
	private static final int IMAGE_HEIGHT = 50;

	/**
	 * The vertical velocity of the plane.
	 */
	private static int VERTICAL_VELOCITY = 8;

	/**
	 * The X-coordinate for the projectile's initial position.
	 */
	private static final int PROJECTILE_X_POSITION = 110;

	/**
	 * The Y-offset for the projectile's initial position.
	 */
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

	/**
	 * Indicates whether a power-up is currently active.
	 */
	private boolean isPowerUpActive = false;

	/**
	 * The velocity multiplier for the plane's movement.
	 */
	private int velocityMultiplier;

	/**
	 * The number of kills by the user plane.
	 */
	private int numberOfKills;

	/**
	 * Constructs a {@code UserPlane} with the specified initial health.
	 *
	 * @param initialHealth the initial health of the user plane
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
	}

	/**
	 * Updates the position of the plane based on its velocity and bounds.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	/**
	 * Activates a power-up that temporarily increases the plane's speed.
	 * The power-up lasts for 20 seconds.
	 */
	public void activatePowerUp() {
		if (isPowerUpActive) {
			return; // Ignore if already active
		}

		isPowerUpActive = true;

		// Max out the velocity
		VERTICAL_VELOCITY = 100;

		// Grace period ends after 20 seconds
		PauseTransition powerUpTimer = new PauseTransition(Duration.seconds(20));
		powerUpTimer.setOnFinished(event -> {
			VERTICAL_VELOCITY = 8; // Reset velocity
			isPowerUpActive = false;
		});
		powerUpTimer.play();
	}

	/**
	 * Updates the state of the plane, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane.
	 *
	 * @return a {@code UserProjectile} representing the projectile fired
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Checks if the plane is currently moving.
	 *
	 * @return {@code true} if the plane is moving; {@code false} otherwise
	 */
	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	/**
	 * Initiates upward movement of the plane.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Initiates downward movement of the plane.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops the plane's movement.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}

	/**
	 * Gets the number of kills by the user plane.
	 *
	 * @return the number of kills
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count of the user plane by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

}
