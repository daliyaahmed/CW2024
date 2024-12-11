package com.example.demo.menus;



import com.example.demo.controller.Controller;
import com.example.demo.levels.*;
import com.example.demo.ui.FullScreenHandler;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class RestartWindow {

    private LevelParent levelParent;
    private final Stage stage;
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";
    private static final String LEVEL_TWO_CLASS_NAME = "com.example.demo.levels.LevelTwo";
    private static final String LEVEL_THREE_CLASS_NAME = "com.example.demo.levels.LevelThree";
    private static final String LEVEL_FOUR_CLASS_NAME = "com.example.demo.levels.LevelFour";

    public RestartWindow(Stage stage, LevelParent levelParent) {
        this.stage = stage;
        this.levelParent = levelParent;
    }

    public void showRestartOptions() {
        // Create a semi-transparent overlay
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        // Add blood splatter effect
        Canvas bloodCanvas = createBloodSplatterCanvas(stage.getWidth(), stage.getHeight());

        // Inner VBox for all elements
        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(20);
        mainLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-border-color: gold; -fx-border-width: 5px; -fx-border-radius: 15; -fx-background-radius: 15;");
        mainLayout.setPadding(new Insets(30));

        // "GAME OVER" Text
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 90));
        gameOverText.setFill(Color.RED);
        gameOverText.setEffect(new DropShadow(10, Color.DARKRED));

        // "Do you want to restart?" Text
        Text restartText = new Text("Do you want to restart?");
        restartText.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 40));
        restartText.setFill(Color.WHITE);

        // Create buttons for restarting levels
        Button restartLevel1Button = createNeonButton("Restart Level 1", Color.CYAN);
        Button restartLevel2Button = createNeonButton("Restart Level 2", Color.LIME);
        Button restartLevel3Button = createNeonButton("Restart Level 3", Color.MAGENTA);
        Button restartLevel4Button = createNeonButton("Restart Level 4", Color.GOLD);

        // Restart buttons layout
        VBox restartButtons = new VBox(10, restartLevel1Button, restartLevel2Button, restartLevel3Button, restartLevel4Button);
        restartButtons.setAlignment(Pos.CENTER);

        // "OR" Text
        Text orText = new Text("OR");
        orText.setFont((Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 90)));
        orText.setFill(Color.GOLD);

        // Quit Button
        Button quitButton = createNeonButton("Quit", Color.RED);
        quitButton.setOnAction(e -> stage.close());

        // Set button actions
        restartLevel1Button.setOnAction(e -> loadLevel(LevelOne.class));
        restartLevel2Button.setOnAction(e -> loadLevel(LevelTwo.class));
        restartLevel3Button.setOnAction(e -> loadLevel(LevelThree.class));
        restartLevel4Button.setOnAction(e -> loadLevel(LevelFour.class));

        // Add all elements to the main layout
        mainLayout.getChildren().addAll(gameOverText, restartText, restartButtons, orText, quitButton);

        // Add the blood canvas and main layout to the overlay
        overlay.getChildren().addAll(bloodCanvas, mainLayout);

        // Create a new scene for the overlay
        Scene scene = new Scene(overlay, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
    private Canvas createBloodSplatterCanvas(double width, double height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Random random = new Random();
        int splatterCount = 20; // Number of splatters

        for (int i = 0; i < splatterCount; i++) {
            double x = random.nextDouble() * width;
            double y = random.nextDouble() * height;
            double radius = random.nextDouble() * 50 + 10; // Random splatter size
            drawBloodSplatter(gc, x, y, radius);
        }

        return canvas;
    }

    private void drawBloodSplatter(GraphicsContext gc, double x, double y, double radius) {
        gc.setFill(Color.DARKRED);
        gc.setGlobalAlpha(0.8); // Semi-transparent blood
        gc.fillOval(x - radius / 2, y - radius / 2, radius, radius);

        // Add random smaller splatters around the main splatter
        Random random = new Random();
        int drops = random.nextInt(5) + 3; // Random number of smaller drops
        for (int i = 0; i < drops; i++) {
            double offsetX = random.nextDouble() * radius - radius / 2;
            double offsetY = random.nextDouble() * radius - radius / 2;
            double dropRadius = radius * (random.nextDouble() * 0.4 + 0.1);
            gc.fillOval(x + offsetX - dropRadius / 2, y + offsetY - dropRadius / 2, dropRadius, dropRadius);
        }
    }

    private void loadLevel(Class<? extends LevelParent> levelClass) {
        try {
            // Dynamically create a level instance
            LevelParent level = levelClass
                    .getConstructor(double.class, double.class, Stage.class)
                    .newInstance(FullScreenHandler.SCREEN_HEIGHT, FullScreenHandler.SCREEN_WIDTH, stage);

            // Initialize and start the level
            stage.setScene(level.initializeScene());
            level.startGame();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load level: " + levelClass.getSimpleName());
        }
    }
    private Button createNeonButton(String text, Color neonColor) {
        Button button = new Button(text);
        button.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/astroz.regular.ttf"), 30));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: " + toHexString(neonColor) + "; -fx-border-width: 3; -fx-border-radius: 15;");

        // Add neon light effect
        DropShadow neonShadow = new DropShadow();
        neonShadow.setColor(neonColor);
        neonShadow.setRadius(20);
        neonShadow.setSpread(0.7);
        button.setEffect(neonShadow);

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: " + toHexString(neonColor) + "; -fx-border-color: " + toHexString(neonColor) + "; -fx-border-width: 3; -fx-border-radius: 15;");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: transparent; -fx-border-color: " + toHexString(neonColor) + "; -fx-border-width: 3; -fx-border-radius: 15;");
        });

        return button;
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }


}
