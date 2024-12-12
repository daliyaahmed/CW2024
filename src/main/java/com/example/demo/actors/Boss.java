package com.example.demo.actors;

import com.example.demo.projectiles.BossProjectile;
import com.example.demo.ui.ShieldImage;

import java.util.*;

/**
 * The {@code Boss} class represents the boss enemy in the game. The boss has unique behaviors such as
 * a move pattern, the ability to fire projectiles, and a shield mechanism that activates probabilistically.
 */
public class Boss extends FighterPlane {

	/**
	 * The name of the image file representing the boss plane.
	 */
	private static final String IMAGE_NAME = "bossplane.png";

	/**
	 * The initial X-coordinate of the boss.
	 */
	private static final double INITIAL_X_POSITION = 1000.0;

	/**
	 * The initial Y-coordinate of the boss.
	 */
	private static final double INITIAL_Y_POSITION = 400;

	/**
	 * The Y-offset for the projectile's initial position.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;

	/**
	 * The rate at which the boss fires projectiles.
	 */
	private static final double BOSS_FIRE_RATE = .04;

	/**
	 * The probability that the boss activates its shield.
	 */
	private static final double BOSS_SHIELD_PROBABILITY = .50;

	/**
	 * The height of the boss's image.
	 */
	private static final int IMAGE_HEIGHT = 300;

	/**
	 * The vertical velocity of the boss.
	 */
	private static final int VERTICAL_VELOCITY = 8;

	/**
	 * The initial health of the boss.
	 */
	private static final int HEALTH = 1;

	/**
	 * The number of moves in a single cycle of the boss's move pattern.
	 */
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;

	/**
	 * Represents zero velocity.
	 */
	private static final int ZERO = 0;

	/**
	 * The maximum number of frames the boss can move in the same direction.
	 */
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;

	/**
	 * The upper Y-coordinate bound for the boss's movement.
	 */
	private static final int Y_POSITION_UPPER_BOUND = -100;

	/**
	 * The lower Y-coordinate bound for the boss's movement.
	 */
	private static final int Y_POSITION_LOWER_BOUND = 475;

	/**
	 * The maximum number of frames the shield can remain active.
	 */
	private static final int MAX_FRAMES_WITH_SHIELD = 500;

	/**
	 * The cooldown period for the shield in frames.
	 */
	private static final int SHIELD_COOLDOWN_FRAMES = 1200;

	/**
	 * Remaining cooldown frames for the shield.
	 */
	private int shieldCooldownFramesRemaining = 0;

	/**
	 * The boss's move pattern.
	 */
	private final List<Integer> movePattern;

	/**
	 * Indicates whether the shield is currently active.
	 */
	private boolean isShielded;

	/**
	 * Tracks consecutive moves in the same direction.
	 */
	private int consecutiveMovesInSameDirection;

	/**
	 * Index of the current move in the move pattern.
	 */
	private int indexOfCurrentMove;

	/**
	 * Frames elapsed with the shield activated.
	 */
	private int framesWithShieldActivated;

	/**
	 * The shield image for the boss.
	 */
	private ShieldImage shieldImage;

	/**
	 * Constructs a {@code Boss} instance and initializes its properties.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
		getShieldImage();
		initializeMovePattern();
	}

	/**
	 * Gets the shield image associated with the boss.
	 *
	 * @return the shield image of the boss
	 */
	public ShieldImage getShieldImage() {
		return shieldImage;
	}

	/**
	 * Updates the boss's position based on its move pattern and bounds.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
		if (isShielded) {
			shieldImage.updatePosition(getLayoutX(), getLayoutY());
		}
	}

	/**
	 * Updates the state of the boss, including its position and shield.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile if the boss is allowed to fire in the current frame.
	 *
	 * @return a {@code BossProjectile} if the boss fires; otherwise, {@code null}
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles the boss taking damage. Damage is only applied if the shield is not active.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
			System.out.println("TAKE DAMAGE BOSS EDITION");
		}
	}

	/**
	 * Gets the health of the boss.
	 *
	 * @return the health of the boss
	 */
	public int getBossHealth() {
		return HEALTH;
	}

	/**
	 * Initializes the move pattern for the boss.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the state of the boss's shield, including activation, deactivation, and cooldown.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			shieldImage.updatePosition(getLayoutX() + getTranslateX(), getLayoutY() + getTranslateY());
		} else if (shieldCooldownFramesRemaining > 0) {
			shieldCooldownFramesRemaining--;
		} else if (shieldShouldBeActivated()) {
			activateShield();
		}

		if (shieldExhausted()) {
			deactivateShield();
		}
	}

	/**
	 * Gets the next move in the boss's move pattern.
	 *
	 * @return the next move in the move pattern
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the boss fires a projectile in the current frame.
	 *
	 * @return {@code true} if the boss fires; {@code false} otherwise
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Gets the initial position of the boss's projectile.
	 *
	 * @return the Y-coordinate for the projectile's initial position
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines if the shield should be activated based on probability.
	 *
	 * @return {@code true} if the shield should be activated; {@code false} otherwise
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Checks if the shield has been active for its maximum duration.
	 *
	 * @return {@code true} if the shield duration is exhausted; {@code false} otherwise
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the shield and displays the shield image.
	 */
	private void activateShield() {
		isShielded = true;
		shieldImage.showShield();
	}

	/**
	 * Deactivates the shield, resets activation duration, and starts the cooldown period.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldCooldownFramesRemaining = SHIELD_COOLDOWN_FRAMES;
		shieldImage.hideShield();
		System.out.println("Shield is off");
	}

}
