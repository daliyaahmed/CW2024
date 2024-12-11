package com.example.demo.actors.l4enemies;


import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.projectiles.BlueJetProjectile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BlueJet extends FighterPlane {

    private static final String IMAGE_NAME = "BlueJetL4.png";
    private static final int IMAGE_HEIGHT = 170;
    private static final int HORIZONTAL_VELOCITY = -12;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 1;
    private static final double FIRE_RATE = .01;
    private static final int VERTICAL_VELOCITY = 6;
    private static final int ZERO = 0;
    private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
    private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
    private double upperBound;
    private double lowerBound;
    private boolean movingUp = true;
    private List<Integer> movePattern;
    private int indexOfCurrentMove;
    private int consecutiveMovesInSameDirection;


    public BlueJet(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);

    }
    public void setMoveUpDownBounds(double upper, double lower) {
        this.upperBound = upper;
        this.lowerBound = lower;
    }
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

    @Override
    public void takeDamage() {
        super.takeDamage();
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new BlueJetProjectile(projectileXPosition, projectileYPostion);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

}
