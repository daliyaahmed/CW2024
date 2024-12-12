package com.example.demo.levels.views;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The {@code LevelViewLevelFour} class extends {@code LevelView} and provides
 * additional features such as a countdown timer and power-up elements for Level Four.
 */
public class LevelViewLevelFour extends LevelView {

    /**
     * The countdown timer text displayed on the screen.
     */
    private final Text countdownTimer;

    /**
     * Timeline for managing the countdown timer animation.
     */
    private Timeline countdownTimeline;

    /**
     * Remaining time for the countdown timer.
     */
    private int remainingTime;

    /**
     * The power-up button element.
     */
    private ImageView powerUpButton;

    /**
     * The text displaying the power-up counter.
     */
    private Text powerUpCounterText;

    /**
     * Constructs a new {@code LevelViewLevelFour} instance.
     *
     * @param root            the root group to which elements will be added
     * @param heartsToDisplay the number of hearts to display on the screen
     */
    public LevelViewLevelFour(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay, false);
        this.remainingTime = 60; // Set initial time for countdown
        countdownTimer = new Text("Time Remaining: ");
        countdownTimer.setStyle("-fx-font-size: 20px; -fx-fill: white; -fx-font-weight: bold;");
        countdownTimer.setX(10);
        countdownTimer.setY(50);
        styleCountdownTimer();
        root.getChildren().add(countdownTimer);
        countdownTimer.toFront(); // Ensure the timer is displayed on top
    }

    /**
     * Starts the countdown timer and executes the provided logic when the timer reaches zero.
     *
     * @param onTimeUp the logic to execute when the timer reaches zero
     */
    public void startCountdownTimer(Runnable onTimeUp) {
        countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    remainingTime--;
                    countdownTimer.setText("Time Remaining: " + remainingTime);
                    styleCountdownTimer();
                    updateCountdownPosition(); // Ensure it stays centered

                    if (remainingTime <= 0) {
                        countdownTimeline.stop();
                        if (onTimeUp != null) {
                            onTimeUp.run(); // Trigger the time-up logic
                        }
                    }
                })
        );
        countdownTimeline.setCycleCount(Timeline.INDEFINITE);
        countdownTimeline.play();
    }

    /**
     * Stops the countdown timer if it is running.
     */
    public void stopCountdownTimer() {
        if (countdownTimeline != null) {
            countdownTimeline.stop();
        }
    }

    /**
     * Adds power-up elements (button and counter) to the root group.
     */
    public void addPowerUpElementsToRoot() {
        if (powerUpButton != null) {
            if (!root.getChildren().contains(powerUpButton)) {
                root.getChildren().addAll(powerUpButton, powerUpCounterText);
                System.out.println("PowerUp button added to root");
            } else {
                System.out.println("PowerUp button already in root");
            }
        }
    }

    /**
     * Applies a style to the countdown timer text, including font and visual effects.
     */
    private void styleCountdownTimer() {
        // Apply CSS for styling
        countdownTimer.applyCss();
        countdownTimer.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 40));
        countdownTimer.setStyle(
                "-fx-font-size: 30px;" + // Font size
                        "-fx-text-fill: linear-gradient(#8B008B, #00008B);" + // Gradient from dark pink to dark blue
                        "-fx-font-weight: bold;" // Bold font for emphasis
        );
        // Add neon-like glow effect using DropShadow
        DropShadow neonGlow = new DropShadow();
        neonGlow.setColor(Color.GOLD);
        neonGlow.setRadius(20);
        neonGlow.setSpread(0.4); // Makes the glow more intense
        countdownTimer.setEffect(neonGlow);
    }

    /**
     * Updates the countdown timer display with the specified remaining time.
     *
     * @param remainingTime the new remaining time to display
     */
    public void updateCountdown(int remainingTime) {
        countdownTimer.setText("Time Remaining: " + remainingTime);
        updateCountdownPosition(); // Recalculate position in case text width changes
    }

    /**
     * Updates the position of the countdown timer on the screen.
     */
    private void updateCountdownPosition() {
        double screenWidth = 1500; // Replace with the actual screen width dynamically
        double textWidth = countdownTimer.getBoundsInLocal().getWidth();
        countdownTimer.setX((screenWidth - textWidth) / 2);
        countdownTimer.setY(50); // Set a fixed Y position near the top
        countdownTimer.toFront(); // Ensure it stays on top after position update
    }
}
