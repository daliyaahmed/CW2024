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
import com.example.demo.ui.FullScreenHandler; // import FullScreenHandler
/**
 * The {@code Controller} class is responsible for managing the game flow,
 * including navigating between different game levels and responding to property changes.
 * This class acts as the central controller for the application, receiving updates from the game levels
 * and managing transitions between them.
 */

public class Controller implements PropertyChangeListener{

	//variable for level one
	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";
	//variable of stage
	private final Stage stage;
	private final FullScreenHandler fullScreenHandler;


	/**
	 * Constructs the {@code Controller} for the application.
	 *
	 * @param stage the {@code Stage} to manage and display the game
	 */
	public Controller(Stage stage) {
		this.stage = stage;
		this.fullScreenHandler = new FullScreenHandler(stage); // initialize FullScreenHandler
	}

	/**
	 * Launches the game by showing the primary stage and starting from level one.
	 *
	 * @throws ClassNotFoundException    if the specified level class cannot be found
	 * @throws NoSuchMethodException     if the constructor for the level class is not found
	 * @throws SecurityException         if the security manager prevents access
	 * @throws InstantiationException    if the instantiation fails for the level class
	 * @throws IllegalAccessException    if the level class or constructor is not accessible
	 * @throws IllegalArgumentException  if arguments provided to constructor are inappropriate
	 * @throws InvocationTargetException if the constructor invocation causes an exception
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

		// Use FullScreenHandler to set the main menu screen to be full-screen
		fullScreenHandler.setFullScreen();
		fullScreenHandler.enableFullScreenMode();
			stage.show();
		System.out.println("Launching game...");
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Transitions the game to the specified level.
	 *
	 * @param className the fully qualified class name of the level to load
	 * @throws ClassNotFoundException    if the specified level class cannot be found
	 * @throws NoSuchMethodException     if the constructor for the level class is not found
	 * @throws SecurityException         if the security manager prevents access
	 * @throws InstantiationException    if the instantiation fails for the level class
	 * @throws IllegalAccessException    if the level class or constructor is not accessible
	 * @throws IllegalArgumentException  if arguments provided to constructor are inappropriate
	 * @throws InvocationTargetException if the constructor invocation causes an exception
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class,Stage.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), stage);
			// Add the controller as a PropertyChangeListener
			myLevel.addPropertyChangeListener( this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();

	}

	/**
	 * Responds to property changes from game levels, such as a request to transition to a different level.
	 *
	 * @param evt the {@code PropertyChangeEvent} containing the updated property information
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
