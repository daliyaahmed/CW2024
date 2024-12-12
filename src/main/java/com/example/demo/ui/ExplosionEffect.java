package com.example.demo.ui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

/**
 * The {@code ExplosionEffect} class generates visual explosion effects
 * using lively expanding particles that fade, move, and disappear.
 */
public class ExplosionEffect {

    private final Group root;

    /**
     * Constructs an {@code ExplosionEffect} instance.
     *
     * @param root the {@link Group} where the explosion will be displayed
     */
    public ExplosionEffect(Group root) {
        this.root = root;
    }

    /**
     * Displays explosions across the screen at random locations.
     *
     * @param count the number of explosions to display
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public void showExplosionsAcrossScreen(int count, double screenWidth, double screenHeight) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * screenWidth;
            double y = random.nextDouble() * screenHeight;
            showExplosion(x, y);
        }
    }

    /**
     * Displays an explosion effect at the specified coordinates.
     *
     * @param x the X-coordinate of the explosion's center
     * @param y the Y-coordinate of the explosion's center
     */
    public void showExplosion(double x, double y) {
        Random random = new Random();
        Group explosionGroup = new Group();

        // Add explosion particles
        for (int i = 0; i < 30; i++) {
            Circle particle = createExplosionParticle(x, y, random);
            animateExplosionParticle(particle, random);
            explosionGroup.getChildren().add(particle);
        }

        root.getChildren().add(explosionGroup);

        // Remove the explosion group after the animation completes
        explosionGroup.setOnMouseClicked(event -> root.getChildren().remove(explosionGroup));
    }

    /**
     * Creates a single explosion particle.
     *
     * @param x      the X-coordinate of the explosion's center
     * @param y      the Y-coordinate of the explosion's center
     * @param random the {@link Random} instance for generating random values
     * @return a {@link Circle} representing the explosion particle
     */
    private Circle createExplosionParticle(double x, double y, Random random) {
        // Alternate between red and orange colors for the particles
        Color color = random.nextBoolean() ? Color.RED : Color.ORANGE;
        Circle particle = new Circle(20 + random.nextDouble() * 10, color);
        particle.setLayoutX(x);
        particle.setLayoutY(y);
        return particle;
    }

    /**
     * Animates a single explosion particle.
     *
     * @param particle the {@link Circle} to animate
     * @param random   the {@link Random} instance for generating random values
     */
    private void animateExplosionParticle(Circle particle, Random random) {
        // Scale (expand) the particle
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), particle);
        scaleTransition.setToX(4 + random.nextDouble() * 2);
        scaleTransition.setToY(4 + random.nextDouble() * 2);

        // Fade out the particle
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), particle);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        // Add slight movement to the particle
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.5), particle);
        translateTransition.setByX((random.nextDouble() - 0.5) * 200);
        translateTransition.setByY((random.nextDouble() - 0.5) * 200);

        // Play animations in parallel
        scaleTransition.play();
        fadeTransition.play();
        translateTransition.play();

        // Remove particle from the root after animation
        fadeTransition.setOnFinished(event -> root.getChildren().remove(particle));
    }
}
