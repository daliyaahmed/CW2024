package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	//private static final String IMAGE_NAME = "/images/shield.png";
	private static final int SHIELD_SIZE = 200;
	private boolean isShieldOn = false; // Track the shield state (on/off)
	
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		//this.setImage(new Image(IMAGE_NAME));
		this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));
		//this.setImage(new Image(getClass().getResource("/com/example/demo/images/*" + IMAGE_NAME).toExternalForm()));
		this.setVisible(true);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	public void showShield() {
		if (!isShieldOn) {
			isShieldOn = true;
			this.setVisible(true); // Show shield
			//System.out.println("Shield Position(in shield image class): " + getLayoutX() + ", " + getLayoutY());
		}



	}
	
	public void hideShield() {

			this.setVisible(false); // Hide shield
			System.out.println("Shield Off shield image class");

	}

	// Method to update the shield position when moving the plane
	public void updatePosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
		//System.out.println("Shield Position Updated(Shield Image class): " + getLayoutX() + ", " + getLayoutY());

	}

}
