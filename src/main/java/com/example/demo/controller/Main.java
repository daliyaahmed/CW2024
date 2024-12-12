package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * The {@code Main} class serves as the entry point for the JavaFX application "Sky Battle."
 * It initializes the game window and sets up the controller for game logic and management.
 *
 * @author Daliya Safdar Ahmed
 */
public class Main extends Application {

	/**
	 * The width of the game screen.
	 */
	private static final int SCREEN_WIDTH = 1500;

	/**
	 * The height of the game screen.
	 */
	private static final int SCREEN_HEIGHT = 800;

	/**
	 * The title of the game window.
	 */
	private static final String TITLE = "Sky Battle";

	/**
	 * The background sound played during the game.
	 */
	public final AudioClip backgroundSound = new AudioClip(
			getClass().getResource("/com/example/demo/sounds/background.wav").toExternalForm());

	/**
	 * Gets the width of the game screen.
	 *
	 * @return the width of the screen
	 */
	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	/**
	 * Gets the height of the game screen.
	 *
	 * @return the height of the screen
	 */
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	/**
	 * Gets the title of the game.
	 *
	 * @return the title of the game
	 */
	public static String getTITLE() {
		return TITLE;
	}

	/**
	 * The main entry point for the JavaFX application.
	 *
	 * @param stage the primary stage for the application
	 * @throws ClassNotFoundException if the main menu class is not found
	 * @throws NoSuchMethodException if the constructor of the main menu class is not found
	 * @throws SecurityException if access to the constructor is denied
	 * @throws InstantiationException if the main menu class cannot be instantiated
	 * @throws IllegalAccessException if the constructor is not accessible
	 * @throws IllegalArgumentException if the arguments to the constructor are invalid
	 * @throws InvocationTargetException if the constructor invocation fails
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
	 * The main method launches the JavaFX application.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		launch();
	}

}
