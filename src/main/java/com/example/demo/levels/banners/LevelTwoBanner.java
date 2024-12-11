package com.example.demo.levels.banners;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.util.Duration;
import javafx.scene.Group;

public class LevelTwoBanner {


    private final ImageView banner;

    // Constructor to create the banner ImageView
    public LevelTwoBanner(double screenWidth, double screenHeight) {
        Image levelOneGif = new Image(getClass().getResource("/com/example/demo/images/Lvl2banner.gif").toExternalForm());
        this.banner = new ImageView(levelOneGif);
        banner.setPreserveRatio(true);

        // Calculate X and Y positions to center the GIF on the screen
        double bannerWidth = levelOneGif.getWidth();
        double bannerHeight = levelOneGif.getHeight();
        banner.setLayoutX((screenWidth - bannerWidth) / 2);
        banner.setLayoutY((screenHeight - bannerHeight) / 2); // Slightly below the top for aesthetics

        // Optional: Add a fade-in effect to make the banner appear smoothly
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), banner);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    // Method to add the banner to the given root group
    public void addTo(Group root) {
        root.getChildren().add(banner);
        // Optional: Remove the banner after 3 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> root.getChildren().remove(banner));
        delay.play();
    }
}
