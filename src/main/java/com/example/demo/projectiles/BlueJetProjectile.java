package com.example.demo.projectiles;


public class BlueJetProjectile extends Projectile {

    private static final String IMAGE_NAME = "Missile1.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final int HORIZONTAL_VELOCITY = -20;

    public BlueJetProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);

    }

    @Override
    public void updateActor() {
        updatePosition();
    }


}
