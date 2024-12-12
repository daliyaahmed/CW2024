package com.example.demo.actors;

/**
 * The {@code ActiveActorDestructible} class is an abstract base class for actors that can be destroyed.
 * It extends {@code ActiveActor} and adds functionality for tracking and handling the destroyed state of the actor.
 */
public abstract class ActiveActorDestructible extends ActiveActor {

	/**
	 * Indicates whether the actor is destroyed.
	 */
	private boolean isDestroyed;

	/**
	 * Constructs an {@code ActiveActorDestructible} with the specified image, dimensions, and initial position.
	 *
	 * @param imageName the name of the image file to represent the actor
	 * @param imageHeight the height of the actor's image
	 * @param initialXPos the initial X-coordinate of the actor
	 * @param initialYPos the initial Y-coordinate of the actor
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor. Subclasses must implement this method to define
	 * how the actor's position changes over time.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the state of the actor. Subclasses must implement this method to define
	 * specific behaviors for updating the actor.
	 */
	public abstract void updateActor();

	/**
	 * Handles the logic for the actor taking damage. Subclasses must implement this method
	 * to define how the actor responds to damage.
	 */
	public abstract void takeDamage();

	/**
	 * Destroys the actor by setting its destroyed state to {@code true}.
	 */
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Resets the destroyed state of the actor to {@code false}.
	 */
	public void notDestroy() {
		setDestroyed(false);
	}

	/**
	 * Sets the destroyed state of the actor.
	 *
	 * @param isDestroyed {@code true} if the actor is destroyed; {@code false} otherwise
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed; {@code false} otherwise
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
}
