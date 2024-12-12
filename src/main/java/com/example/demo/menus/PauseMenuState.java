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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The {@code PauseMenuState} class manages the game's pause menu, providing
 * functionality to pause, resume, and quit the game. It handles UI elements
 * like buttons and overlays, and ensures smooth transitions between states.
 */
public class PauseMenuState {

    private static final String PAUSE_BUTTON = "/com/example/demo/images/pauseBut.png";
    private static final String RESUME_BUTTON = "/com/example/demo/images/resumeBut.png";
    private static final String QUIT_BUTTON = "/com/example/demo/images/quitBut.png";

    private final Group root;
    private final Timeline timeline;
    private final double screenWidth;
    private final double screenHeight;
    private final Runnable resumeCallback;
    private final Stage stage;

    /**
     * Constructor to initialize the pause menu state.
     *
     * @param root           the root group of the game scene
     * @param timeline       the game's main timeline
     * @param screenWidth    the width of the game screen
     * @param screenHeight   the height of the game screen
     * @param resumeCallback a callback to resume the game
     * @param stage          the main stage of the application
     */
    public PauseMenuState(Group root, Timeline timeline, double screenWidth, double screenHeight,
                          Runnable resumeCallback, Stage stage) {
        this.root = root;
        this.timeline = timeline;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.resumeCallback = resumeCallback;
        this.stage = stage;
    }

    /**
     * Creates a pause button that, when clicked, pauses the game and displays the pause menu.
     *
     * @return a {@link Button} representing the pause button
     */
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

    /**
     * Displays the pause menu overlay and stops the game timeline.
     *
     * @param pauseButton the button used to trigger the pause menu
     */
    private void showPauseWindow(Button pauseButton) {
        timeline.pause();

        StackPane pauseOverlay = new StackPane();
        pauseOverlay.setPrefSize(screenWidth, screenHeight);
        pauseOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); ");

        VBox borderBox = new VBox();
        borderBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-border-color:  gold; -fx-border-width: 5px; -fx-border-radius: 20; -fx-background-radius: 20;");
        borderBox.setPadding(new Insets(20));
        borderBox.setSpacing(30);
        borderBox.setAlignment(Pos.CENTER);
        borderBox.setMaxWidth(400);
        borderBox.setMaxHeight(300);

        Label pauseLabel = new Label("GAME PAUSED");
        pauseLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 30));

        DropShadow neonGlow = new DropShadow();
        neonGlow.setColor(Color.GOLD);
        neonGlow.setRadius(20);
        neonGlow.setSpread(0.4);
        pauseLabel.setEffect(neonGlow);

        Button resumeButton = createResumeButton(pauseOverlay, pauseButton);
        Button quitLevelButton = createQuitLevelButton();

        borderBox.getChildren().addAll(pauseLabel, resumeButton, quitLevelButton);
        pauseOverlay.getChildren().add(borderBox);
        root.getChildren().add(pauseOverlay);
    }

    /**
     * Creates the resume button to resume the game from the pause state.
     *
     * @param pauseOverlay the pause overlay to be removed when resuming
     * @param pauseButton  the pause button to re-enable after resuming
     * @return a {@link Button} for resuming the game
     */
    private Button createResumeButton(StackPane pauseOverlay, Button pauseButton) {
        Image resumeButImage = new Image(getClass().getResourceAsStream(RESUME_BUTTON));
        ImageView resumeImageView = new ImageView(resumeButImage);
        resumeImageView.setFitWidth(100);
        resumeImageView.setFitHeight(90);

        Button resumeButton = new Button();
        resumeButton.setGraphic(resumeImageView);
        resumeButton.setStyle("-fx-background-color: transparent;");
        resumeButton.setOnAction(event -> {
            root.getChildren().remove(pauseOverlay);
            pauseButton.setDisable(true);
            resumeGameWithCountdown(pauseButton);
        });

        return resumeButton;
    }

    /**
     * Creates the quit button to exit the current level and return to the main menu.
     *
     * @return a {@link Button} for quitting the level
     */
    private Button createQuitLevelButton() {
        Image quitLevelImage = new Image(getClass().getResourceAsStream(QUIT_BUTTON));
        ImageView quitLevelImageView = new ImageView(quitLevelImage);
        quitLevelImageView.setFitWidth(100);
        quitLevelImageView.setFitHeight(90);

        Button quitLevelButton = new Button();
        quitLevelButton.setGraphic(quitLevelImageView);
        quitLevelButton.setStyle("-fx-background-color: transparent;");
        quitLevelButton.setOnAction(event -> {
            if (stage != null) {
                root.getChildren().clear();
                MainMenu mainMenu = new MainMenu(stage);
                stage.setFullScreenExitHint("");
                stage.setFullScreen(false);
                stage.setFullScreen(true);
                mainMenu.showMenu();
            }
        });

        return quitLevelButton;
    }

    /**
     * Resumes the game with a countdown displayed on the screen.
     *
     * @param pauseButton the pause button to re-enable after the countdown
     */
    private void resumeGameWithCountdown(Button pauseButton) {
        Label countdownLabel = new Label("3");
        countdownLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 90));
        countdownLabel.setLayoutX(screenWidth / 2 - 50);
        countdownLabel.setLayoutY(screenHeight / 2 - 100);

        DropShadow neonShadow = new DropShadow();
        neonShadow.setColor(Color.GOLD);
        neonShadow.setRadius(50);
        neonShadow.setSpread(0.8);

        InnerShadow innerGlow = new InnerShadow();
        innerGlow.setColor(Color.GOLD);
        innerGlow.setRadius(25);
        innerGlow.setChoke(0.4);

        countdownLabel.setEffect(neonShadow);
        countdownLabel.setEffect(innerGlow);

        root.getChildren().add(countdownLabel);

        Timeline countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> countdownLabel.setText("2")),
                new KeyFrame(Duration.seconds(2), e -> countdownLabel.setText("1")),
                new KeyFrame(Duration.seconds(3), e -> {
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
