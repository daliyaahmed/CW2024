package com.example.demo.ui;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;
/**
 * The {@code ConfettiEffectManager} class handles the creation and animation of
 * confetti effects on the game screen. This class is used to add celebratory visual
 * effects, such as falling and rotating confetti particles, to enhance user experience.
 */
public class ConfettiEffectManager {
    /**
     * The root group where confetti effects will be added.
     */
    private final Group root;
    /**
     * The width of the screen where confetti effects will be displayed.
     */
    private final double screenWidth;
    /**
     * The height of the screen where confetti effects will be displayed.
     */
    private final double screenHeight;
    /**
     * Constructs a new {@code ConfettiEffectManager}.
     *
     * @param root        the {@link Group} to which confetti effects will be added
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public ConfettiEffectManager(Group root, double screenWidth, double screenHeight) {
        this.root = root;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    /**
     * Displays the confetti effect by creating and animating multiple confetti particles.
     */
    public void showConfettiEffect() {
        Random random = new Random();
        Group confettiGroup = new Group();

        root.getChildren().add(confettiGroup);
        confettiGroup.toFront();

        for (int i = 0; i < 50; i++) {
            Circle confetti = createConfettiParticle(random);
            animateConfettiParticle(confetti, random);
        }
    }
    /**
     * Creates a single confetti particle with random color and position.
     *
     * @param random the {@link Random} instance used to generate random values
     * @return a {@link Circle} representing the confetti particle
     */
    private Circle createConfettiParticle(Random random) {
        Circle confetti = new Circle(5, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        confetti.setLayoutX(random.nextDouble() * screenWidth);
        confetti.setLayoutY(-10);
        return confetti;
    }
    /**
     * Animates a single confetti particle with both translation and rotation effects.
     *
     * @param confetti the {@link Circle} representing the confetti particle
     * @param random   the {@link Random} instance used to generate random values for animation
     */
    private void animateConfettiParticle(Circle confetti, Random random) {
        root.getChildren().add(confetti);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2 + random.nextDouble()), confetti);
        translateTransition.setByY(screenHeight + 50);
        translateTransition.setCycleCount(1);
        translateTransition.setOnFinished(event -> root.getChildren().remove(confetti));

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2 + random.nextDouble()), confetti);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, rotateTransition);
        parallelTransition.play();
    }
}
