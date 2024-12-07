package com.example.demo.controller;


import com.example.demo.ui.FullScreenHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class MainMenu {

    private final Stage stage;
    private final FullScreenHandler fullScreenHandler;
    private static final String BACKGROUND_MAIN_MENU = "/com/example/demo/images/mainscreen.png";

    public MainMenu(Stage stage) {
        this.stage = stage;
        this.fullScreenHandler = new FullScreenHandler(stage); // initialize FullScreenHandler
    }


    public void showMenu() {


        // Load background image
        Image backgroundImage = new Image(getClass().getResource("/com/example/demo/images/mainscreen.png").toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Bind the image size to the stage dimensions to ensure it resizes dynamically
        backgroundImageView.fitWidthProperty().bind(stage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(stage.heightProperty());
        backgroundImageView.setPreserveRatio(false); // Ensure it stretches to fill the screen

        // Set the screen to full-screen using FullScreenHandler
        fullScreenHandler.setFullScreen();
        fullScreenHandler.enableFullScreenMode();


        // Create buttons
        Button playButton = createNeonButton("PLAY", Color.CYAN);
        Button guideButton = createNeonButton("GUIDE TO PLAY", Color.LIME);
        Button quitButton = createNeonButton("QUIT", Color.MAGENTA);

        // Set button actions
        playButton.setOnAction(e -> launchGame());
        guideButton.setOnAction(e -> showGuide());
        quitButton.setOnAction(e -> stage.close());



        // Create layout for buttons
        VBox buttonLayout = new VBox(20); // Main button layout
        buttonLayout.getChildren().addAll(playButton, guideButton, quitButton);
        buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
        buttonLayout.setPadding(new Insets(0, 0, 100, 0)); // Padding to align buttons properly at the bottom of the screen


        // Create root layout
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, buttonLayout);
        StackPane.setAlignment(buttonLayout, Pos.BOTTOM_CENTER);
        buttonLayout.setPadding(new Insets(0, 0, 30, 0)); // Padding from the bottom
        StackPane.setMargin(buttonLayout, new Insets(50, 0, 50, 0)); // Adjust margin from the bottom for better spacing

        // Set scene
        Scene scene = new Scene(root, FullScreenHandler.SCREEN_WIDTH, FullScreenHandler.SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.setFullScreen(true);  // Ensure full-screen mode is re-enabled.
        stage.show();
    }

    private Button createNeonButton(String text, Color neonColor) {
        Button button = new Button(text);
        button.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: " + toHexString(neonColor) + "; -fx-padding: 10 20 10 20; -fx-border-color: " + toHexString(neonColor) + "; -fx-border-width: 3; -fx-border-radius: 15; ");

        // Creating neon light effect
        DropShadow neonShadow = new DropShadow();
        neonShadow.setColor(neonColor);
        neonShadow.setRadius(20);
        neonShadow.setSpread(0.7);

        // Adding inner glow to make it look more 3D and shiny
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(neonColor);
        innerShadow.setRadius(10);
        innerShadow.setOffsetX(0);
        innerShadow.setOffsetY(0);

        // Combine effects
        button.setEffect(neonShadow);
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: transparent; -fx-padding: 10 20 10 20; -fx-border-color: " + toHexString(neonColor) + "; -fx-border-width: 3; -fx-border-radius: 15; -fx-background-radius: 15; -fx-font-weight: bold;");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: " + toHexString(neonColor) + "; -fx-padding: 10 20 10 20; -fx-border-color: " + toHexString(neonColor) + "; -fx-border-width: 3; -fx-border-radius: 15; -fx-background-radius: 15; -fx-font-weight: bold;");
        });

        return button;
    }


    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }

    private void launchGame() {
        try {
            Controller controller = new Controller(stage);
            controller.launchGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showGuide() {
        // You can create a new scene or alert for the guide.
        System.out.println("Guide to play coming soon...");
    }
}
