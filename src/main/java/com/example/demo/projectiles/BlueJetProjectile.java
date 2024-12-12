package com.example.demo.projectiles;

/**
 * The {@code BlueJetProjectile} class represents a projectile fired by a Blue Jet.
 * It extends the {@code Projectile} class and provides specific behavior for the Blue Jet projectile,
 * including its image, size, and movement logic.
 */
public class BlueJetProjectile extends Projectile {

    private static final String IMAGE_NAME = "Missile1.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final int HORIZONTAL_VELOCITY = -20;

    /**
     * Constructs a {@code BlueJetProjectile} with the specified initial position.
     *
     * @param initialXPos the initial X-coordinate of the projectile
     * @param initialYPos the initial Y-coordinate of the projectile
     */
    public BlueJetProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
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
