package com.example.demo.ui;

import javafx.stage.Stage;
import javafx.stage.Screen;

public class FullScreenHandler {

    private Stage stage;
    public static final int SCREEN_WIDTH = (int) Screen.getPrimary().getBounds().getWidth();
    public static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getBounds().getHeight();

    public FullScreenHandler(Stage stage) {
        this.stage = stage;
    }

    public void setFullScreen() {

        // Set the stage dimensions to match the screen bounds
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
    }

    public void enableFullScreenMode() {
        stage.setFullScreen(true);
    }
}

