package com.example.demo.actors;

import com.example.demo.projectiles.BossProjectile;
import com.example.demo.ui.ShieldImage;

import java.util.*;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .50;
	private static final int IMAGE_HEIGHT = 300;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 1;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 500;
	private static final int SHIELD_COOLDOWN_FRAMES = 1200; // Assuming 60 frames per second, this gives 20 seconds
	private int shieldCooldownFramesRemaining = 0;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private ShieldImage shieldImage;

	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		shieldImage = new ShieldImage(INITIAL_X_POSITION,INITIAL_Y_POSITION);
		getShieldImage();
		initializeMovePattern();
	}
	// Add this getter method
	public ShieldImage getShieldImage() {
		return shieldImage;
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
		if (isShielded) {
			shieldImage.updatePosition(getLayoutX(), getLayoutY()); // Update shield position

		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
			System.out.println("TAKE DAMAGE BOSS EDITION " );
		}
	}

	public int getBossHealth(){
		return HEALTH;
	}
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			shieldImage.updatePosition(getLayoutX() + getTranslateX(), getLayoutY() + getTranslateY());
		} else if (shieldCooldownFramesRemaining > 0) {
			shieldCooldownFramesRemaining--; // Decrease cooldown
		} else if (shieldShouldBeActivated()) {
			activateShield();
		}

		if (shieldExhausted()) {
			deactivateShield();
		}
	}

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

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
		shieldImage.showShield(); // Show shield when activated
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldCooldownFramesRemaining = SHIELD_COOLDOWN_FRAMES; // Start the cooldown period
		shieldImage.hideShield(); // Hide shield when deactivated
		System.out.println("Shield is off");
	}

}