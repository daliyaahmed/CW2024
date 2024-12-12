package com.example.demo.projectiles;

/**
 * The {@code PafProjectile} class represents a projectile fired by PAF jet units in the game.
 * It extends the {@code Projectile} class and defines specific attributes such as its image,
 * size, initial position, and horizontal velocity.
 */
public class PafProjectile extends Projectile {

    private static final String IMAGE_NAME = "pafjet2bullet.png";
    private static final int IMAGE_HEIGHT = 20;
    private static final int HORIZONTAL_VELOCITY = -10;

    /**
     * Constructs a {@code PafProjectile} at the specified initial X and Y coordinates.
     *
     * @param initialXPos the initial X-coordinate of the projectile
     * @param initialYPos the initial Y-coordinate of the projectile
     */
    public PafProjectile(double initialXPos, double initialYPos) {
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
