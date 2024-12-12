package com.example.demo.levels.views;

import com.example.demo.ui.PowerUpManager;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * The {@code LevelViewLevelThree} class represents the view for Level Three of the game,
 * including power-up elements like a power-up button and counter display.
 */
public class LevelViewLevelThree extends LevelView {

    /**
     * The image view representing the power-up button.
     */
    private ImageView powerUpButton;

    /**
     * The text display for the power-up counter.
     */
    private Text powerUpCounterText;

    /**
     * The manager handling power-up logic.
     */
    private PowerUpManager powerUpManager;

    /**
     * The total number of power-ups available.
     */
    private int remainingPowerUps = 3;

    /**
     * Constructs a new {@code LevelViewLevelThree} instance.
     *
     * @param root            the root group to which elements will be added
     * @param heartsToDisplay the number of hearts to display on the screen
     */
    public LevelViewLevelThree(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay, true);
    }

    /**
     * Adds the power-up elements (button and counter) to the root group.
     * Ensures the elements are not added multiple times.
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
     * Gets the text display for the power-up counter.
     *
     * @return the {@code Text} representing the power-up counter
     */
    public Text getPowerUpCounterText() {
        return powerUpCounterText;
    }
}
