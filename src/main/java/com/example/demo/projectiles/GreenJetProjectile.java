package com.example.demo.projectiles;


public class GreenJetProjectile extends Projectile {

    private static final String IMAGE_NAME = "missile4Final.png";
    private static final int IMAGE_HEIGHT = 120;
    private static final int HORIZONTAL_VELOCITY = -20;

    public GreenJetProjectile(double initialXPos, double initialYPos) {
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
