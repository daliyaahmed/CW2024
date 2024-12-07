package com.example.demo.ui;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class PowerUpManager {
    private static final String POWERUP_IMAGE_PATH = "/com/example/demo/images/powerup.png";
    private static final double POWERUP_BUTTON_X = 1350; // Adjust as needed
    private static final double POWERUP_BUTTON_Y = 10;
    private static final double POWERUP_COUNTER_X = POWERUP_BUTTON_X - 50;

    private ImageView powerUpButton;
    private Text powerUpCounterText;
    private int remainingPowerUps = 3;
    private Timeline powerUpTimer;
    private boolean isPowerUpActive = false;

    public PowerUpManager(Group root) {
        createPowerUpButton(root);
    }

    private void createPowerUpButton(Group root) {
        try {
            // Load PowerUp image
            Image powerUpImage = new Image(getClass().getResourceAsStream(POWERUP_IMAGE_PATH));

            // Create PowerUp button
            powerUpButton = new ImageView(powerUpImage);
            powerUpButton.setFitWidth(50);
            powerUpButton.setFitHeight(50);
            powerUpButton.setX(POWERUP_BUTTON_X);
            powerUpButton.setY(POWERUP_BUTTON_Y);

            // Create PowerUp counter
            powerUpCounterText = new Text("x" + remainingPowerUps);
            powerUpCounterText.setX(POWERUP_COUNTER_X);
            powerUpCounterText.setY(POWERUP_BUTTON_Y + 30);
            powerUpCounterText.setFill(Color.WHITE);
            powerUpCounterText.setStyle("-fx-font-size: 20px;");

            // Add to root
            root.getChildren().addAll(powerUpButton, powerUpCounterText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activatePowerUp() {
        if (remainingPowerUps > 0 && !isPowerUpActive) {
            remainingPowerUps--;
            isPowerUpActive = true;
            updatePowerUpCounter();


            // Create a 10-second timer
            powerUpTimer = new Timeline(new KeyFrame(Duration.seconds(10), event -> deactivatePowerUp()));
            powerUpTimer.play();
        }
    }

    public void deactivatePowerUp() {
        isPowerUpActive = false;
        if (powerUpTimer != null) {
            powerUpTimer.stop();
        }
    }

    private void updatePowerUpCounter() {
        if (powerUpCounterText != null) {
            powerUpCounterText.setText("x" + remainingPowerUps);

            // Disable button if no power-ups left
            if (remainingPowerUps == 0) {
                powerUpButton.setOpacity(0.5);
                powerUpButton.setDisable(true);
            }
        }
    }

    public boolean isPowerUpActive() {
        return isPowerUpActive;
    }

    public boolean hasPowerUpsRemaining() {
        return remainingPowerUps > 0;
    }

    public ImageView getPowerUpButton() {
        return powerUpButton;
    }
}
