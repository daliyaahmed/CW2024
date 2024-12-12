package com.example.demo.actors.l4enemies;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.projectiles.GreenJetProjectile;


/**
 * The {@code GreenJet} class represents a level four enemy plane in the game.
 * It moves vertically between specified bounds and fires projectiles at a specified rate.
 */
public class GreenJet extends FighterPlane {
    /**
     * The name of the image file representing the plane.
     */
    private static final String IMAGE_NAME = "GreenJetL4.png";
    /**
     * The height of the plane's image.
     */
    private static final int IMAGE_HEIGHT = 100;
    /**
     * The horizontal velocity of the plane.
     */
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    /**
     * The X-offset for the projectile's position.
     */
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    /**
     * The Y-offset for the projectile's position.
     */
    private static final int INITIAL_HEALTH = 1;
    /**
     * The initial health of the plane.
     */
    private static final double FIRE_RATE = .01;
    /**
     * The firing rate of the plane's projectiles.
     */
    private static final int VERTICAL_VELOCITY = 6;
    /**
     * The upper bound for vertical movement.
     */
    private double upperBound;
    /**
     * The lower bound for vertical movement.
     */
    private double lowerBound;
    /**
     * A flag indicating whether the plane is moving up.
     */
    private boolean movingUp = true;
    /**
     * Constructs a {@code GreenJet} with the specified initial position.
     *
     * @param initialXPos the initial X-coordinate of the plane
     * @param initialYPos the initial Y-coordinate of the plane
     */
    public GreenJet(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }
    /**
     * Sets the upper and lower bounds for the plane's vertical movement.
     *
     * @param upper the upper bound for vertical movement
     * @param lower the lower bound for vertical movement
     */
    public void setMoveUpDownBounds(double upper, double lower) {
        this.upperBound = upper;
        this.lowerBound = lower;
    }
    /**
     * Updates the position of the plane by moving it vertically between the specified bounds.
     */
    @Override
    public void updatePosition() {
        // Calculate the new Y position
        double newY = getTranslateY() + (movingUp ? -VERTICAL_VELOCITY : VERTICAL_VELOCITY);

        // Check if the new position is within bounds
        if (newY < upperBound) {
            newY = upperBound; // Reset to upper bound
            movingUp = false;  // Change direction to down
        } else if (newY > lowerBound) {
            newY = lowerBound; // Reset to lower bound
            movingUp = true;   // Change direction to up
        }

        // Update the jet's position
        setTranslateY(newY);
    }
    /**
     * Fires a {@code GreenJetProjectile} if the random chance is less than the firing rate.
     *
     * @return a new {@code GreenJetProjectile} if the plane fires; otherwise, {@code null}
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new GreenJetProjectile(projectileXPosition, projectileYPostion);
        }
        return null;
    }
    /**
     * Handles the logic for the plane taking damage.
     */
    @Override
    public void takeDamage() {
        super.takeDamage();
    }
    /**
     * Updates the state of the plane, including its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

}
