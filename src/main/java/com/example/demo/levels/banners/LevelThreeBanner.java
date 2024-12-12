package com.example.demo.levels.banners;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.Group;

/**
 * The {@code LevelThreeBanner} class is responsible for displaying a banner
 * at the start of Level Three. It includes animations for smooth appearance
 * and timed removal of the banner.
 */
public class LevelThreeBanner {

    /** The ImageView displaying the banner. */
    private final ImageView banner;

    /**
     * Constructs a {@code LevelThreeBanner} object with the given screen dimensions.
     * It initializes the banner image, positions it at the center of the screen,
     * and applies a fade-in effect for smooth appearance.
     *
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public LevelThreeBanner(double screenWidth, double screenHeight) {
        Image levelOneGif = new Image(getClass().getResource("/com/example/demo/images/Lvl3banner.gif").toExternalForm());
        this.banner = new ImageView(levelOneGif);
        banner.setPreserveRatio(true);

        // Calculate X and Y positions to center the GIF on the screen
        double bannerWidth = levelOneGif.getWidth();
        double bannerHeight = levelOneGif.getHeight();
        banner.setLayoutX((screenWidth - bannerWidth) / 2);
        banner.setLayoutY((screenHeight - bannerHeight) / 2); // Slightly below the top for aesthetics

        // Add a fade-in effect to make the banner appear smoothly
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), banner);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    /**
     * Adds the banner to the specified root group and removes it after 3 seconds.
     *
     * @param root the {@code Group} to which the banner will be added
     */
    public void addTo(Group root) {
        root.getChildren().add(banner);

        // Remove the banner after 3 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> root.getChildren().remove(banner));
        delay.play();
    }
}
