package com.example.demo.menus;



import com.example.demo.controller.MainMenu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PauseMenuState {
    private static final String PAUSE_BUTTON = "/com/example/demo/images/pauseBut.png";
    private static final String RESUME_BUTTON = "/com/example/demo/images/resumeBut.png";
    private static final String QUIT_BUTTON = "/com/example/demo/images/quitBut.png";

    private final Group root;
    private final Timeline timeline;
    private final double screenWidth;
    private final double screenHeight;
    private final Runnable resumeCallback;
    // Add a Stage variable to keep track of the main window stage
    private final Stage stage;

    public PauseMenuState(Group root, Timeline timeline, double screenWidth, double screenHeight,
                     Runnable resumeCallback,  Stage stage) {
        this.root = root;
        this.timeline = timeline;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.resumeCallback = resumeCallback;


        this.stage = stage;
    }

    public Button createPauseButton() {
        Image pauseButtonImage = new Image(getClass().getResourceAsStream(PAUSE_BUTTON));
        ImageView pauseImageView = new ImageView(pauseButtonImage);
        pauseImageView.setFitWidth(100);
        pauseImageView.setFitHeight(90);

        Button pauseButton = new Button();
        pauseButton.setGraphic(pauseImageView);
        pauseButton.setLayoutX(screenWidth - 120);
        pauseButton.setLayoutY(10);
        pauseButton.setStyle("-fx-background-color: transparent;");
        pauseButton.setOnAction(event -> showPauseWindow(pauseButton));

        return pauseButton;
    }

    private void showPauseWindow(Button pauseButton) {
        // Pause the game
        timeline.pause();

        // Create a pause overlay
        StackPane pauseOverlay = new StackPane();
        pauseOverlay.setPrefSize(screenWidth, screenHeight);
        pauseOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); ");

        // Inner box with border and smaller size
        VBox borderBox = new VBox();
        borderBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-border-color:  gold; -fx-border-width: 5px; -fx-border-radius: 20; -fx-background-radius: 20;");
        borderBox.setPadding(new Insets(20)); // Adds space inside the border
        borderBox.setSpacing(30); // Adds space between elements
        borderBox.setAlignment(Pos.CENTER);
        borderBox.setMaxWidth(400); // Limits the width of the box
        borderBox.setMaxHeight(300); // Limits the height of the box

        // Pause message
        Label pauseLabel = new Label("GAME PAUSED");
        pauseLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 30));

        // Add a neon-like glow effect using a DropShadow
        DropShadow neonGlow = new DropShadow();
        neonGlow.setColor(Color.GOLD);
        neonGlow.setRadius(20);
        neonGlow.setSpread(0.4); // Makes the glow more intense

        // Add the glow effect to the label
        pauseLabel.setEffect(neonGlow);
        // resume game button
        Image resumeButImage = new Image(getClass().getResourceAsStream(RESUME_BUTTON));
        ImageView resumeImageView = new ImageView(resumeButImage);
        resumeImageView.setFitWidth(100);
        resumeImageView.setFitHeight(90);

        Button resumeButton = new Button();
        resumeButton.setGraphic(resumeImageView);
        resumeButton.setStyle("-fx-background-color: transparent;");
        resumeButton.setOnAction(event ->  {
            root.getChildren().remove(pauseOverlay);
            pauseButton.setDisable(true);
            resumeGameWithCountdown(pauseButton);
        });

        // quit level button
        Image quitLevelImage = new Image(getClass().getResourceAsStream(QUIT_BUTTON));
        ImageView quitLevelImageView = new ImageView(quitLevelImage);
        quitLevelImageView.setFitWidth(100);
        quitLevelImageView.setFitHeight(90);

        Button quitLevelButton = new Button();
        quitLevelButton.setGraphic(quitLevelImageView);
        quitLevelButton.setStyle("-fx-background-color: transparent;");
        quitLevelButton.setOnAction(event -> {
            if (stage != null) {
                // Clear all nodes in the root to prevent any leftover content.
                root.getChildren().clear();
                // Create an instance of MainMenu and show the main menu
                MainMenu mainMenu = new MainMenu(stage);

                // Set stage properties and reset to full-screen mode.
                stage.setFullScreenExitHint(""); // Clear full-screen exit hint for a clean UI.
                stage.setFullScreen(false);      // Set it to non-full-screen first to refresh properties.
                stage.setFullScreen(true);       // Then set it back to full-screen mode.


                mainMenu.showMenu();
            }
        });

        // Add all elements to the inner box
        borderBox.getChildren().addAll(pauseLabel, resumeButton, quitLevelButton);

        pauseOverlay.getChildren().add(borderBox);
        root.getChildren().add(pauseOverlay);
    }

    private void resumeGameWithCountdown(Button pauseButton) {
        // Create a Label for the countdown with initial text "3"
        Label countdownLabel = new Label("3");
        countdownLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 90));
        //countdownLabel.setStyle("-fx-font-size: 100px; -fx-text-fill: white;");
        countdownLabel.setLayoutX(screenWidth / 2 - 50);  // Adjust position to center the label
        countdownLabel.setLayoutY(screenHeight / 2 - 100);

        // Create neon effect for the countdown label
        DropShadow neonShadow = new DropShadow();
        neonShadow.setColor(Color.GOLD);
        neonShadow.setRadius(50);
        neonShadow.setSpread(0.8);

        InnerShadow innerGlow = new InnerShadow();
        innerGlow.setColor(Color.GOLD);
        innerGlow.setRadius(25); // Adjust radius to make it more visible
        innerGlow.setChoke(0.4); // Choke to make the inner glow more pronounced

        // Combine effects
        countdownLabel.setEffect(neonShadow);
        countdownLabel.setEffect(innerGlow);

        // Add the countdown label to the root layout
        root.getChildren().add(countdownLabel);

        // Timeline for the countdown effect
        Timeline countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    countdownLabel.setText("2");
                    neonShadow.setColor(Color.GOLD);
                    innerGlow.setColor(Color.GOLD);
                    countdownLabel.setEffect(neonShadow);
                    countdownLabel.setEffect(innerGlow);
                }),
                new KeyFrame(Duration.seconds(2), e -> {
                    countdownLabel.setText("1");
                    neonShadow.setColor(Color.GOLD);
                    innerGlow.setColor(Color.GOLD);
                    countdownLabel.setEffect(neonShadow);
                    countdownLabel.setEffect(innerGlow);
                }),
                new KeyFrame(Duration.seconds(3), e -> {
                    // Remove the countdown label and resume the game
                    root.getChildren().remove(countdownLabel);
                    pauseButton.setDisable(false);
                    if (resumeCallback != null) {
                        resumeCallback.run();
                    }
                })
        );
        countdownTimeline.setCycleCount(1);
        countdownTimeline.play();
    }
}
