package com.example.demo.projectiles;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * The {@code Projectile} class serves as an abstract base class for all projectile types in the game.
 * It extends the {@code ActiveActorDestructible} class, inheriting its behavior while defining
 * specific methods for handling damage and updating the projectile's position.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a {@code Projectile} with the specified attributes.
	 *
	 * @param imageName    the name of the image file representing the projectile
	 * @param imageHeight  the height of the image for scaling purposes
	 * @param initialXPos  the initial X-coordinate of the projectile
	 * @param initialYPos  the initial Y-coordinate of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the damage behavior for the projectile.
	 * When a projectile takes damage, it is destroyed immediately.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile.
	 * This method must be implemented by subclasses to define specific movement logic.
	 */
	@Override
	public abstract void updatePosition();
}
