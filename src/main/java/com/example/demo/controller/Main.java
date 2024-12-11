package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The {@code Main} class serves as the entry point for the JavaFX application "Sky Battle."
 * It initializes the game window and sets up the controller for game logic and management.
 *
 * @author Daliya Safdar Ahmed
 */

public class  Main extends Application {

	// Constants for screen dimensions and title
	private static final int SCREEN_WIDTH = 1500;
	private static final int SCREEN_HEIGHT = 800;
	public final AudioClip backgroundSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/background.wav").toExternalForm());
	private static final String TITLE = "Sky Battle";

	// Instance variable to hold the game controller
	private Controller myController;

	/**
	 * Retrieves the screen width of the application window.
	 *
	 * @return the screen width in pixels
	 */

	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	/**
	 * Retrieves the screen height of the application window.
	 *
	 * @return the screen height in pixels
	 */

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	/**
	 * Retrieves the title of the game window.
	 *
	 * @return the title of the application
	 */
	public static String getTITLE() {
		return TITLE;
	}

	/**
	 * Initializes and starts the JavaFX application.
	 * Sets up the stage (main game window) with title, dimensions, and a {@code Controller}.
	 *
	 * @param stage the primary stage for this application
	 * @throws ClassNotFoundException if the {@code Controller} class cannot be found
	 * @throws NoSuchMethodException if the {@code Controller} constructor cannot be accessed
	 * @throws SecurityException if a security violation occurs while accessing the {@code Controller}
	 * @throws InstantiationException if an error occurs while instantiating the {@code Controller}
	 * @throws IllegalAccessException if the {@code Controller} constructor is inaccessible
	 * @throws IllegalArgumentException if an invalid argument is passed to the {@code Controller} constructor
	 * @throws InvocationTargetException if the {@code Controller} constructor throws an exception
	 */

	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(getTITLE());
		stage.setResizable(true);
		stage.setHeight(getScreenHeight());
		stage.setWidth(getScreenWidth());
		// Set the cycle count to INDEFINITE to loop the sound
		backgroundSound.setCycleCount(MediaPlayer.INDEFINITE);
		backgroundSound.play();
		// Show main menu
		MainMenu mainMenu = new MainMenu(stage);
		mainMenu.showMenu();
	}
	/**
	 * The main method that launches the JavaFX application.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		launch();
	}

	/**
	 * Retrieves the current game {@code Controller}.
	 *
	 * @return the current {@code Controller} instance
	 */

	public Controller getMyController() {
		return myController;
	}

	/**
	 * Sets the {@code Controller} for the application.
	 *
	 * @param myController the {@code Controller} to manage the game
	 */
	public void setMyController(Controller myController) {
		this.myController = myController;
	}
}
