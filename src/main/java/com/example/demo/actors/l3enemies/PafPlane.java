package com.example.demo.actors.l3enemies;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.projectiles.EnemyProjectile;
import com.example.demo.projectiles.PafProjectile;


/**
 * The {@code PafPlane} class represents a level three enemy plane in the game.
 * This class is an Easter egg representing a Pakistani Air Force jet.
 * The projectile fired by this plane is modeled after an actual missile used by the Pakistani Air Force.
 */
public class PafPlane extends FighterPlane {

    /**
     * The name of the image file representing the plane.
     */
    private static final String IMAGE_NAME = "pafjet2.png";
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
     * The firing rate of the plane's projectiles.
     */
    private static final double FIRE_RATE = .01;
    /**
     * Constructs a {@code PafPlane} with the specified initial position.
     *
     * @param initialXPos the initial X-coordinate of the plane
     * @param initialYPos the initial Y-coordinate of the plane
     */
    public PafPlane(double initialXPos, double initialYPos) {
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
     * Fires a {@code PafProjectile} if the random chance is less than the firing rate.
     *
     * @return a new {@code PafProjectile} if the plane fires; otherwise, {@code null}
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new PafProjectile(projectileXPosition, projectileYPostion);
        }
        return null;
    }
    /**
     * Sets the position of the plane.
     *
     * @param x the new X-coordinate of the plane
     * @param y the new Y-coordinate of the plane
     */
    public void setPosition(double x, double y) {
        setX(x);
        setY(y);
    }
    /**
     * Destroys the plane and allows for additional logic if needed.
     */
    @Override
    public void destroy() {
        super.destroy();
    }
    /**
     * Updates the state of the plane, including its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

}
