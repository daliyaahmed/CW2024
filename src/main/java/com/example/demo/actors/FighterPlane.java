package com.example.demo.actors;

/**
 * The {@code FighterPlane} class serves as an abstract base class for fighter planes in the game.
 * Fighter planes are destructible actors that can fire projectiles and take damage.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	/**
	 * The current health of the fighter plane.
	 */
	private int health;

	/**
	 * Constructs a {@code FighterPlane} with the specified image, dimensions, initial position, and health.
	 *
	 * @param imageName the name of the image file representing the plane
	 * @param imageHeight the height of the plane's image
	 * @param initialXPos the initial X-coordinate of the plane
	 * @param initialYPos the initial Y-coordinate of the plane
	 * @param health the initial health of the plane
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the plane.
	 *
	 * @return an instance of {@code ActiveActorDestructible} representing the projectile fired
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Handles the logic for the plane taking damage. If health reaches zero, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		System.out.println(this.getClass().getSimpleName() + " health: " + health  + " | Source: " + this.getClass().getSimpleName());
		if (healthAtZero() && !isDestroyed()) {
			this.destroy();
			System.out.println(this.getClass().getSimpleName() + " is destroyed!");
		}
	}

	/**
	 * Calculates the X-coordinate for a projectile fired by the plane.
	 *
	 * @param xPositionOffset the offset to add to the plane's current X-coordinate
	 * @return the X-coordinate for the projectile
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y-coordinate for a projectile fired by the plane.
	 *
	 * @param yPositionOffset the offset to add to the plane's current Y-coordinate
	 * @return the Y-coordinate for the projectile
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the plane's health has reached zero.
	 *
	 * @return {@code true} if the health is zero or less; {@code false} otherwise
	 */
	private boolean healthAtZero() {
		return health <= 0;
	}

	/**
	 * Gets the current health of the plane.
	 *
	 * @return the current health of the plane
	 */
	public int getHealth() {
		return health;
	}

}
