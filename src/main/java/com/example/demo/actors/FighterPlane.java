package com.example.demo.actors;

import com.example.demo.actors.ActiveActorDestructible;

public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	public abstract ActiveActorDestructible fireProjectile();
	
	@Override
	public void takeDamage() {
		health--;
		System.out.println(this.getClass().getSimpleName() + " health: " + health);
		if (healthAtZero() && !isDestroyed() ) {
			this.destroy();
			System.out.println(this.getClass().getSimpleName() + " is destroyed!");
		}
	}

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {
		return health <= 0;
	}

	public int getHealth() {
		return health;
	}
		
}
