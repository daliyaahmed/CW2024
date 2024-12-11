package com.example.demo.levels.views;
import com.example.demo.ui.PowerUpManager;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LevelViewLevelThree extends LevelView {

    private static final String POWER_UP_BUTTON = "/com/example/demo/images/powerUp.png";
    private static final double POWERUP_BUTTON_X_POSITION = 1120; // Adjust based on your layout
    private static final double POWERUP_BUTTON_Y_POSITION = 75;
    private static final double POWERUP_COUNTER_X_POSITION = 40; // Left of the button
    private static final double POWERUP_COUNTER_Y_POSITION = 25;

    //private Button powerUpButton;
    private Text powerUpCounter;
    // PowerUp related fields
    private ImageView powerUpButton;

    private Text powerUpCounterText;
    private PowerUpManager powerUpManager;
    private int remainingPowerUps = 3; // Total number of power-ups

    public LevelViewLevelThree(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay, true);


    }



    public ImageView getPowerUpButton() {
        return powerUpButton;
    }
    public boolean hasPowerUpsRemaining() {
        return remainingPowerUps > 0;
    }

    public void decrementPowerUpCounter() {
        if (remainingPowerUps > 0) {
            remainingPowerUps--;
            powerUpCounter.setText("x" + remainingPowerUps);
            if (remainingPowerUps == 0) {
                powerUpButton.setDisable(true); // Disable when no power-ups left
            }
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



    public Text getPowerUpCounterText() {
        return powerUpCounterText;
    }
}
