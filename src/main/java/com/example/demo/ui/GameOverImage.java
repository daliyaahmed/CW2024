package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;

public class GameOverImage extends ImageView {
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) );
//		setImage(ImageSetUp.getImageList().get(ImageSetUp.getGameOver()));
		setLayoutX(450);
		setLayoutY(50);
		// Scale down the image (0.5 means 50% smaller)
		Scale scale = new Scale(0.5, 0.5);
		getTransforms().add(scale);
	}

}
