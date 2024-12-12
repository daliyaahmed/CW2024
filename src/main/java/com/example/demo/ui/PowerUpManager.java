package com.example.demo.ui;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

/**
 * The {@code PowerUpManager} class manages the PowerUp functionality in the game.
 * It includes a button to activate the PowerUp, a counter to track remaining PowerUps,
 * and a timer to deactivate the PowerUp after its duration expires.
 */
public class PowerUpManager {

    /** Path to the PowerUp image. */
    private static final String POWERUP_IMAGE_PATH = "/com/example/demo/images/powerup.png";

    /** X and Y positions for the PowerUp button and counter. */
    private static final double POWERUP_BUTTON_X = 1350;
    private static final double POWERUP_BUTTON_Y = 10;
    private static final double POWERUP_COUNTER_X = POWERUP_BUTTON_X - 50;

    /** The PowerUp button image. */
    private ImageView powerUpButton;

    /** The text displaying the remaining PowerUps count. */
    private Text powerUpCounterText;

    /** The number of remaining PowerUps. */
    private int remainingPowerUps = 3;

    /** Timer to deactivate the PowerUp after 10 seconds. */
    private Timeline powerUpTimer;

    /** Flag to indicate if the PowerUp is currently active. */
    private boolean isPowerUpActive = false;

    /**
     * Constructs a {@code PowerUpManager} and initializes the PowerUp button and counter.
     *
     * @param root the root {@link Group} where the PowerUp elements will be added
     */
    public PowerUpManager(Group root) {
        createPowerUpButton(root);
    }

    /**
     * Creates the PowerUp button and counter and adds them to the root.
     *
     * @param root the root {@link Group} where the PowerUp elements will be added
     */
    private void createPowerUpButton(Group root) {
        try {
            // Load PowerUp image
            Image powerUpImage = new Image(getClass().getResourceAsStream(POWERUP_IMAGE_PATH));

            // Create PowerUp button
            powerUpButton = new ImageView(powerUpImage);
            powerUpButton.setFitWidth(70);
            powerUpButton.setFitHeight(60);
            powerUpButton.setX(POWERUP_BUTTON_X);
            powerUpButton.setY(POWERUP_BUTTON_Y);

            // Create PowerUp counter
            powerUpCounterText = new Text("x" + remainingPowerUps + " ");
            powerUpCounterText.setX(POWERUP_COUNTER_X);
            powerUpCounterText.setY(POWERUP_BUTTON_Y + 40);
            powerUpCounterText.setFill(Color.WHITE);
            powerUpCounterText.setStyle("-fx-font-size: 40px;");
            powerUpCounterText.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 30));

            // Add to root
            root.getChildren().addAll(powerUpButton, powerUpCounterText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Activates the PowerUp if there are remaining PowerUps and none is currently active.
     * Starts a 10-second timer to automatically deactivate the PowerUp.
     */
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

    /**
     * Deactivates the PowerUp and stops the timer if it is running.
     */
    public void deactivatePowerUp() {
        isPowerUpActive = false;
        if (powerUpTimer != null) {
            powerUpTimer.stop();
        }
    }

    /**
     * Updates the PowerUp counter text and disables the button if no PowerUps remain.
     */
    private void updatePowerUpCounter() {
        if (powerUpCounterText != null) {
            powerUpCounterText.setText("x" + remainingPowerUps + " ");

            // Disable button if no PowerUps left
            if (remainingPowerUps == 0) {
                powerUpButton.setOpacity(0.5);
                powerUpButton.setDisable(true);
            }
        }
    }

    /**
     * Checks if there are any remaining PowerUps.
     *
     * @return {@code true} if there are remaining PowerUps, {@code false} otherwise
     */
    public boolean hasPowerUpsRemaining() {
        return remainingPowerUps > 0;
    }

    /**
     * Returns the PowerUp button {@link ImageView}.
     *
     * @return the PowerUp button
     */
    public ImageView getPowerUpButton() {
        return powerUpButton;
    }
}
