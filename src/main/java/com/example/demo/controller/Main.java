package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public static String getTITLE() {
		return TITLE;
	}

	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(getTITLE());
		stage.setResizable(false);
		stage.setHeight(getScreenHeight());
		stage.setWidth(getScreenWidth());
		setMyController(new Controller(stage));
		getMyController().launchGame();
	}

	public static void main(String[] args) {
		launch();
	}

	public Controller getMyController() {
		return myController;
	}

	public void setMyController(Controller myController) {
		this.myController = myController;
	}
}