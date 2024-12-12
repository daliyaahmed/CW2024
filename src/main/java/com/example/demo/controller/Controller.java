package com.example.demo.controller;

//added property change listener imports

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.levels.LevelParent;
import com.example.demo.ui.FullScreenHandler;

/**
 * The {@code Controller} class manages the flow of the game, including transitioning
 * between levels and responding to property changes.
 */
public class Controller implements PropertyChangeListener {

	/**
	 * The class name for the first level of the game.
	 */
	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";

	/**
	 * The primary stage for displaying the game.
	 */
	private final Stage stage;

	/**
	 * A handler for enabling and managing full-screen mode.
	 */
	private final FullScreenHandler fullScreenHandler;

	/**
	 * Constructs a {@code Controller} with the specified stage.
	 *
	 * @param stage the primary stage for the game
	 */
	public Controller(Stage stage) {
		this.stage = stage;
		this.fullScreenHandler = new FullScreenHandler(stage); // initialize FullScreenHandler
	}

	/**
	 * Launches the game by setting up full-screen mode and starting the first level.
	 *
	 * @throws ClassNotFoundException if the level class is not found
	 * @throws NoSuchMethodException if the constructor of the level class is not found
	 * @throws SecurityException if access to the constructor is denied
	 * @throws InstantiationException if the level class cannot be instantiated
	 * @throws IllegalAccessException if the constructor is not accessible
	 * @throws IllegalArgumentException if the arguments to the constructor are invalid
	 * @throws InvocationTargetException if the constructor invocation fails
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// Use FullScreenHandler to set the main menu screen to be full-screen
		fullScreenHandler.setFullScreen();
		fullScreenHandler.enableFullScreenMode();
		stage.show();
		System.out.println("Launching game...");
		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Transitions to the specified level.
	 *
	 * @param className the fully qualified class name of the level
	 * @throws ClassNotFoundException if the level class is not found
	 * @throws NoSuchMethodException if the constructor of the level class is not found
	 * @throws SecurityException if access to the constructor is denied
	 * @throws InstantiationException if the level class cannot be instantiated
	 * @throws IllegalAccessException if the constructor is not accessible
	 * @throws IllegalArgumentException if the arguments to the constructor are invalid
	 * @throws InvocationTargetException if the constructor invocation fails
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class, Stage.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), stage);
		// Add the controller as a PropertyChangeListener
		myLevel.addPropertyChangeListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	/**
	 * Handles property change events, such as transitioning to a new level.
	 *
	 * @param evt the property change event
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("level".equals(evt.getPropertyName())) {
			try {
				goToLevel((String) evt.getNewValue());
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getClass().toString());
				alert.show();
			}
		}
	}

}
