package com.example.demo.controller;

import com.example.demo.actors.UserPlane;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 * Handles game input for controlling the user's plane and firing projectiles.
 * The {@code GameInputHandler} class processes key press and release events
 * to manage user movement and actions.
 */
public class GameInputHandler {
    /**
     * The user's plane controlled by input events.
     */
    private final UserPlane user;
    /**
     * The action to be executed when the fire projectile key is pressed.
     */
    private final Runnable fireProjectileAction;
    /**
     * Constructs a {@code GameInputHandler} with the specified user plane and action
     * for firing projectiles.
     *
     * @param user                 the {@link UserPlane} controlled by the player
     * @param fireProjectileAction the {@link Runnable} action to fire a projectile
     */
    public GameInputHandler(UserPlane user, Runnable fireProjectileAction) {
        this.user = user;
        this.fireProjectileAction = fireProjectileAction;
    }
    /**
     * Returns the key press event handler to manage movement and actions.
     *
     * @return an {@link EventHandler} for {@link KeyEvent} to handle key presses
     */
    public EventHandler<KeyEvent> getKeyPressedHandler() {
        return event -> {
            KeyCode kc = event.getCode();
            if (kc == KeyCode.UP) user.moveUp();
            if (kc == KeyCode.DOWN) user.moveDown();
            if (kc == KeyCode.SPACE) fireProjectileAction.run();
        };
    }
    /**
     * Returns the key release event handler to stop the user's movement.
     *
     * @return an {@link EventHandler} for {@link KeyEvent} to handle key releases
     */
    public EventHandler<KeyEvent> getKeyReleasedHandler() {
        return event -> {
            KeyCode kc = event.getCode();
            if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
        };
    }
}
