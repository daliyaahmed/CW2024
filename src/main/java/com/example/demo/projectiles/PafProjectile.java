
package com.example.demo.projectiles;

public class PafProjectile extends Projectile {

    private static final String IMAGE_NAME = "pafjet2bullet.png";
    private static final int IMAGE_HEIGHT = 20;
    private static final int HORIZONTAL_VELOCITY = -10;

    public PafProjectile(double initialXPos, double initialYPos) {
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
