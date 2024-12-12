package com.example.demo.levels.views;

import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

/**
 * The {@code LevelViewLevelTwo} class provides the view for Level Two, including
 * the functionality to display and manage a shield image.
 */
public class LevelViewLevelTwo extends LevelView {

	/**
	 * The X-coordinate position for the shield image.
	 */
	private static final int SHIELD_X_POSITION = 1150;

	/**
	 * The Y-coordinate position for the shield image.
	 */
	private static final int SHIELD_Y_POSITION = 500;

	/**
	 * The root group where all visual elements for the level are added.
	 */
	private final Group root;

	/**
	 * The shield image displayed in this level.
	 */
	private final ShieldImage shieldImage;

	/**
	 * Constructs a new {@code LevelViewLevelTwo} instance.
	 *
	 * @param root            the root group to which level elements are added
	 * @param heartsToDisplay the number of hearts to display on the screen
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay, true);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds the shield image to the root group.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	/**
	 * Displays the shield image in the level.
	 */
	public void showShield() {
		shieldImage.showShield();
	}

	/**
	 * Hides the shield image in the level.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

}
