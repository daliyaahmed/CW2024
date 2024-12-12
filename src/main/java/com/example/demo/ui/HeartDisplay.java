package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The {@code HeartDisplay} class represents a visual display of hearts
 * to indicate the player's remaining lives or health.
 * The hearts are arranged horizontally in an {@link HBox}.
 */
public class HeartDisplay {

	/** The file path for the heart image. */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	/** The height of each heart image. */
	private static final int HEART_HEIGHT = 50;

	/** The index of the first item in the heart container. */
	private static final int INDEX_OF_FIRST_ITEM = 0;

	/** The container for the heart images. */
	private HBox container;

	/** The X-coordinate of the heart container. */
	private double containerXPosition;

	/** The Y-coordinate of the heart container. */
	private double containerYPosition;

	/** The number of hearts to display initially. */
	private int numberOfHeartsToDisplay;

	/**
	 * Constructs a {@code HeartDisplay} with specified position and number of hearts.
	 *
	 * @param xPosition the X-coordinate of the heart container
	 * @param yPosition the Y-coordinate of the heart container
	 * @param heartsToDisplay the initial number of hearts to display
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container for the hearts as an {@link HBox}.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the hearts by creating and adding the specified number of hearts to the container.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes one heart from the display, starting with the first heart.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Returns the container holding the hearts.
	 *
	 * @return the {@link HBox} container of the hearts
	 */
	public HBox getContainer() {
		return container;
	}
}
