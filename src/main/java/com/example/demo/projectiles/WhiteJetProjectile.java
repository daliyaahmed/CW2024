package com.example.demo.projectiles;


public class WhiteJetProjectile extends Projectile {

    private static final String IMAGE_NAME = "Missile3.png";
    private static final int IMAGE_HEIGHT = 120;
    private static final int HORIZONTAL_VELOCITY = -10;

    public WhiteJetProjectile(double initialXPos, double initialYPos) {
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
