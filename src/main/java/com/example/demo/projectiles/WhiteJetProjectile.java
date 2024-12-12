package com.example.demo.projectiles;

/**
 * The {@code WhiteJetProjectile} class represents a projectile fired by a white jet.
 * It extends the {@code Projectile} class and provides specific behavior for movement
 * and updating its state.
 */
public class WhiteJetProjectile extends Projectile {

    /** The name of the image file representing the white jet's projectile. */
    private static final String IMAGE_NAME = "Missile3.png";

    /** The height of the projectile's image for scaling purposes. */
    private static final int IMAGE_HEIGHT = 120;

    /** The horizontal velocity of the white jet's projectile. */
    private static final int HORIZONTAL_VELOCITY = -10;

    /**
     * Constructs a {@code WhiteJetProjectile} with the specified initial position.
     *
     * @param initialXPos the initial X-coordinate of the projectile
     * @param initialYPos the initial Y-coordinate of the projectile
     */
    public WhiteJetProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the white jet's projectile by moving it horizontally.
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
