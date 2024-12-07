package com.example.demo.actors;

import com.example.demo.projectiles.EnemyProjectile;
import com.example.demo.projectiles.PafProjectile;

//PafPlane is level three's enemy plane
//an easter egg in this game is that i added a pakistani air force jet
//and also the projectile is an actual missile that the pakistani air force jet's missile
//so, in short, Paf stands for Pakistan Air Force
public class PafPlane extends FighterPlane {

    private static final String IMAGE_NAME = "pafjet2.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 1;
    private static final double FIRE_RATE = .01;

    public PafPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new PafProjectile(projectileXPosition, projectileYPostion);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

}
