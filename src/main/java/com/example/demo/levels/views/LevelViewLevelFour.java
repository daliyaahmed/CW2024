package com.example.demo.levels.views;


import com.example.demo.levels.views.LevelView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LevelViewLevelFour extends LevelView {

    private final Text countdownTimer;
    private Timeline countdownTimeline;
    private int remainingTime;
    private ImageView powerUpButton;
    private Text powerUpCounterText;

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
    public void stopCountdownTimer() {
        if (countdownTimeline != null) {
            countdownTimeline.stop();
        }
    }
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
    public void updateCountdown(int remainingTime) {
        countdownTimer.setText("Time Remaining: " + remainingTime);
        updateCountdownPosition(); // Recalculate position in case text width changes
    }
    private void updateCountdownPosition() {

        double screenWidth = 1500; // Replace with the actual screen width dynamically
        double textWidth = countdownTimer.getBoundsInLocal().getWidth();
        countdownTimer.setX((screenWidth - textWidth) / 2);
        countdownTimer.setY(50); // Set a fixed Y position near the top
        countdownTimer.toFront(); // Ensure it stays on top after position update
    }
}
