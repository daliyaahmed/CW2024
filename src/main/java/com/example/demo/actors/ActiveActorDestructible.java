package com.example.demo.actors;


import com.example.demo.actors.ActiveActor;

public abstract class ActiveActorDestructible extends ActiveActor  {

	private boolean isDestroyed;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();


	public abstract void takeDamage();


	public void destroy() {
		setDestroyed(true);
	}

	public void notDestroy(){setDestroyed(false);}
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	public boolean isDestroyed() {
		return isDestroyed;
	}

}
