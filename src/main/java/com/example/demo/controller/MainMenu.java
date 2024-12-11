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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
        button.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 30));
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
        // Create a semi-transparent overlay
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        // Inner VBox for all elements
        VBox guideLayout = new VBox();
        guideLayout.setAlignment(Pos.CENTER);
        guideLayout.setSpacing(20);
        guideLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-border-color: gold; -fx-border-width: 5px; -fx-border-radius: 15; -fx-background-radius: 15;");
        guideLayout.setPadding(new Insets(30));

        // "GUIDE TO PLAY" Title
        Text guideTitle = new Text("GUIDE TO PLAY");
        guideTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 50));
        guideTitle.setFill(Color.GOLD);
        guideTitle.setEffect(new DropShadow(10, Color.DARKGOLDENROD));

        // Instructions using TextFlow
        TextFlow instructions = new TextFlow();
        instructions.setStyle("-fx-text-alignment: center;");
        instructions.setPadding(new Insets(10));

        // Add instructional text with styled key terms
        instructions.getChildren().addAll(
                createInstructionText("Use "),
                createKeyText("UP arrow"),
                createInstructionText(" to move up.\nUse "),
                createKeyText("DOWN arrow"),
                createInstructionText(" to move down.\nPress "),
                createKeyText("SPACEBAR"),
                createInstructionText(" to shoot.\n\nClick "),
                createKeyText("PAUSE button"),
                createInstructionText(" to pause the game.\nIn the PAUSE menu, click "),
                createKeyText("RESUME"),
                createInstructionText(" to continue the game.\nIn the PAUSE menu, click "),
                createKeyText("QUIT"),
                createInstructionText(" to go to the Main Menu.\nClick "),
                createKeyText("POWER-UP button"),
                createInstructionText(" to speed up vertically in Level Three and Four.\nTo win "),
                createKeyText("Level Four"),
                createInstructionText(", you need to "),
                createKeyText("kill"),
                createInstructionText(" a number of each "),
                createKeyText("Blue"),
                createInstructionText(", "),
                createKeyText("White"),
                createInstructionText(" and "),
                createKeyText("Green"),
                createInstructionText(" jets.\n")
        );


        // Back to Main Menu Button
        Button backButton = createNeonButton("BACK TO MAIN MENU", Color.GOLD);
        backButton.setOnAction(e -> {
            // Return to the main menu
            showMenu();
        });

        // Add all elements to the guide layout
        guideLayout.getChildren().addAll(guideTitle, instructions, backButton);

        // Add guide layout to the overlay
        overlay.getChildren().add(guideLayout);

        // Create a new scene for the overlay
        Scene scene = new Scene(overlay, FullScreenHandler.SCREEN_WIDTH, FullScreenHandler.SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
    // Helper method for regular text
    private Text createInstructionText(String text) {
        Text instruction = new Text(text);
        instruction.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 30));
        instruction.setFill(Color.WHITE);
        return instruction;
    }
    // Helper method for key terms
    private Text createKeyText(String text) {
        Text keyText = new Text(text);
        keyText.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 30));
        keyText.setFill(Color.RED);
        return keyText;
    }
}
