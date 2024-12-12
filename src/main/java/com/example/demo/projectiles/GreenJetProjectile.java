package com.example.demo.projectiles;

/**
 * The {@code GreenJetProjectile} class represents a projectile fired by green jet units in the game.
 * It extends the {@code Projectile} class and defines specific attributes, including its image,
 * size, initial position, and horizontal velocity.
 */
public class GreenJetProjectile extends Projectile {

    private static final String IMAGE_NAME = "missile4Final.png";
    private static final int IMAGE_HEIGHT = 120;
    private static final int HORIZONTAL_VELOCITY = -20;

    /**
     * Constructs a {@code GreenJetProjectile} at the specified initial X and Y coordinates.
     *
     * @param initialXPos the initial X-coordinate of the projectile
     * @param initialYPos the initial Y-coordinate of the projectile
     */
    public GreenJetProjectile(double initialXPos, double initialYPos) {
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
