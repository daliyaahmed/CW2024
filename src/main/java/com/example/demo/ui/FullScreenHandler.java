package com.example.demo.ui;

import javafx.stage.Stage;
import javafx.stage.Screen;

/**
 * The {@code FullScreenHandler} class manages the full-screen settings for a given {@link Stage}.
 * It provides methods to set the stage dimensions to match the screen bounds and enable full-screen mode.
 */
public class FullScreenHandler {

    /** The {@code Stage} instance to be managed for full-screen settings. */
    private Stage stage;

    /** The width of the primary screen in pixels. */
    public static final int SCREEN_WIDTH = (int) Screen.getPrimary().getBounds().getWidth();

    /** The height of the primary screen in pixels. */
    public static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getBounds().getHeight();

    /**
     * Constructs a {@code FullScreenHandler} for the specified stage.
     *
     * @param stage the {@link Stage} to be managed for full-screen settings
     */
    public FullScreenHandler(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the dimensions of the stage to match the screen bounds.
     */
    public void setFullScreen() {
        // Set the stage dimensions to match the screen bounds
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
    }

    /**
     * Enables full-screen mode for the stage.
     */
    public void enableFullScreenMode() {
        stage.setFullScreen(true);
    }
}
