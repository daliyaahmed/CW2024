package com.example.demo.levels;

import java.util.*;

import com.example.demo.controller.GameInputHandler;
import com.example.demo.physics.Collision;
import com.example.demo.levels.views.LevelView;
import com.example.demo.levels.views.LevelViewLevelFour;
import com.example.demo.levels.views.LevelViewLevelThree;
import com.example.demo.menus.PauseMenuState;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.UserPlane;
import com.example.demo.menus.RestartWindow;
import com.example.demo.ui.ConfettiEffectManager;
import com.example.demo.ui.ExplosionEffect;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static com.example.demo.controller.Main.getScreenHeight;

//observer addition import



public abstract class LevelParent  {
	/**
	 * Adjustment value for determining the maximum Y position for enemy units,
	 * accounting for UI elements.
	 */
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

	/**
	 * Delay in milliseconds for each game loop iteration in the timeline.
	 */
	private static final int MILLISECOND_DELAY = 50;

	/**
	 * PropertyChangeSupport for notifying listeners of level transitions.
	 */
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/**
	 * The height of the game screen in pixels.
	 */
	private final double screenHeight;

	/**
	 * The width of the game screen in pixels.
	 */
	private final double screenWidth;

	/**
	 * The maximum Y position where enemy units can spawn.
	 */
	private final double enemyMaximumYPosition;

	/**
	 * The root group containing all elements of the game scene.
	 */
	private final Group root;

	/**
	 * The timeline that controls the game loop and updates the scene at regular intervals.
	 */
	public final Timeline timeline;

	/**
	 * The user's plane controlled during the game level.
	 */
	public UserPlane user;

	/**
	 * The main scene for this level.
	 */
	private final Scene scene;

	/**
	 * The background image for the level.
	 */
	private final ImageView background;

	/**
	 * The collision manager for detecting and handling collisions between game entities.
	 */
	public final Collision collision;

	/**
	 * The pause menu state for managing pause and resume functionality.
	 */
	private PauseMenuState pauseMenuState;

	/**
	 * The primary stage used to display the game.
	 */
	private final Stage stage;

	/**
	 * List of friendly units, including the user's plane and any allied entities.
	 */
	private final List<ActiveActorDestructible> friendlyUnits;

	/**
	 * List of enemy units currently active in the level.
	 */
	public final List<ActiveActorDestructible> enemyUnits;

	/**
	 * List of projectiles fired by the user.
	 */
	public final List<ActiveActorDestructible> userProjectiles;

	/**
	 * List of projectiles fired by enemy units.
	 */
	public final List<ActiveActorDestructible> enemyProjectiles;

	/**
	 * List of fighter planes active in the level.
	 */
	private final List<FighterPlane> fighterPlanes;

	/**
	 * Level view for managing UI and gameplay-specific features in Level Three.
	 */
	private final LevelViewLevelThree levelViewLevelThree;

	/**
	 * Level view for managing UI and gameplay-specific features in Level Four.
	 */
	private final LevelViewLevelFour levelViewLevelFour;

	/**
	 * The main level view managing health, kill count, and other UI elements.
	 */
	private final LevelView levelView;


	/**
	 * Audio clip for playing the sound effect when firing a projectile.
	 */
	private final AudioClip fireSound = new AudioClip(
			getClass().getResource("/com/example/demo/sounds/shoot.wav").toExternalForm()
	);

	/**
	 * Background music played during the level.
	 */
	public final AudioClip backgroundSound = new AudioClip(
			getClass().getResource("/com/example/demo/sounds/background.wav").toExternalForm()
	);
	public final AudioClip winSound = new AudioClip(
			getClass().getResource("/com/example/demo/sounds/win.mp3").toExternalForm()
	);
	public final AudioClip loseSound = new AudioClip(
			getClass().getResource("/com/example/demo/sounds/lose.mp3").toExternalForm()
	);
	/**
	 * Tracks the current number of enemies active in the level.
	 */
	private int currentNumberOfEnemies;
	/**
	 * Input handler for managing user controls.
	 */
	private GameInputHandler inputHandler;
	/**
	 * Manages all projectiles in the game, including their creation, movement, and collisions.
	 * Handles both user-fired and enemy-fired projectiles to ensure proper gameplay interactions.
	 */

	/**
	 * Constructs a new {@code LevelParent} instance.
	 *
	 * @param backgroundImageName the path to the background image
	 * @param screenHeight        the height of the screen
	 * @param screenWidth         the width of the screen
	 * @param playerInitialHealth the initial health of the user plane
	 * @param stage               the primary stage of the application
	 */

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth,  Stage stage) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.fighterPlanes = new ArrayList<>();
		this.stage = stage;
		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
			// Initialize CollisionManager
			this.collision = new Collision(
					user,
					friendlyUnits,
					enemyUnits,
					userProjectiles,
					enemyProjectiles,
					screenWidth
			);
			// Initialize PauseMenu
			this.pauseMenuState = new PauseMenuState(
					root,
					timeline,
					screenWidth,
					screenHeight,
					() -> timeline.play(), // Resume callback
					stage
			);

			this.levelViewLevelThree = new LevelViewLevelThree(root,5);
			this.levelViewLevelFour = new LevelViewLevelFour(root,10);
	}
	/**
	 * Abstract method to initialize the friendly units in the level.
	 * This method is implemented by subclasses to add specific friendly units
	 * (like the player or allied entities) to the level's scene.
	 */
	protected abstract void initializeFriendlyUnits();
	/**
	 * Abstract method to check if the game is over.
	 * Subclasses implement this to determine whether the level is completed
	 * or if the player has lost, based on specific conditions like player health or objectives.
	 */
	protected abstract void checkIfGameOver();
	/**
	 * Abstract method to spawn enemy units in the level.
	 * Subclasses implement this to define the behavior for spawning
	 * enemies, including their quantity, positions, and timing.
	 */
	protected abstract void spawnEnemyUnits();
	/**
	 * Abstract method to instantiate the level view.
	 * This method is implemented by subclasses to create and configure
	 * the specific {@code LevelView} object for the current level.
	 *
	 * @return the {@code LevelView} instance for the level
	 */
	protected abstract LevelView instantiateLevelView();
	/**
	 * Initializes the scene for the level, including background, friendly units, and UI elements.
	 *
	 * @return the initialized {@code Scene} object
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}
	/**
	 * Starts the game by focusing the background and starting the timeline.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}
	/**
	 * Fires a property change event to transition to the next level.
	 *
	 * @param levelName the name of the next level
	 */
	public void goToNextLevel(String levelName) {
		pcs.firePropertyChange("level", null, levelName);
		System.out.println("Transitioning to next level: " + levelName);
	}

	/**
	 * Adds a property change listener for level transitions.
	 *
	 * @param listener the property change listener
	 */
	//added listener
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	/**
	 * Updates the scene, including spawning enemies, updating actors,
	 * handling collisions, and checking game state.
	 */
	public void updateScene() {
		spawnEnemyUnits();
		// Add PowerUp button and counter to the root
		levelViewLevelThree.addPowerUpElementsToRoot();
		levelViewLevelFour.addPowerUpElementsToRoot();
		updateActors();
		generateEnemyFire();

		updateNumberOfEnemies();
		// Use CollisionManager for all collision-related logic
		collision.handleEnemyPenetration();
		collision.handleUserProjectileCollisions();
		collision.handleEnemyProjectileCollisions();
		collision.handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}
	/**
	 * Initializes the timeline for the game loop.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}
	/**
	 * Initializes the background image and user controls for the level.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		// Create input handler with a method reference to fireProjectile
		this.inputHandler = new GameInputHandler(user, this::fireProjectile);
		// Set the event handlers using the new input handler
		background.setOnKeyPressed(inputHandler.getKeyPressedHandler());
		background.setOnKeyReleased(inputHandler.getKeyReleasedHandler());
		root.getChildren().add(background);
		// Replace the pause button creation in initializeBackground
		Button pauseButton = pauseMenuState.createPauseButton();
		root.getChildren().add(pauseButton);
	}
	/**
	 * Fires a projectile from the user plane.
	 */
	private void fireProjectile() {
		// Play sound effect
		fireSound.play();
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Spawns enemy projectiles.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> {
			// Only let FighterPlane enemies generate projectiles
			if (enemy instanceof FighterPlane) {
				FighterPlane fighterPlane = (FighterPlane) enemy;
				spawnEnemyProjectile(fighterPlane.fireProjectile());
			}
		});
	}
	/**
	 * Spawns an enemy projectile by adding it to the root group and tracking it in the enemy projectile list.
	 *
	 * @param projectile the {@link ActiveActorDestructible} projectile fired by an enemy unit
	 */
	public void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}



	/**
	 * Gets the {@link LevelView} instance associated with this level.
	 * The LevelView manages UI elements such as health displays and kill counts.
	 *
	 * @return the {@link LevelView} instance for this level.
	 */
	protected LevelView getLevelView() {
		return levelView;
	}


	/**
	 * Updates all actors in the scene.
	 */
	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}
	/**
	 * Removes all destroyed actors from the scene and root.
	 */
	public void removeAllDestroyedActors() {
		collision.removeDestroyedActors(friendlyUnits);
		collision.removeDestroyedActors(enemyUnits);
		collision.removeDestroyedActors(userProjectiles);
		collision.removeDestroyedActors(enemyProjectiles);

		// Remove destroyed actors from the root
		root.getChildren().removeIf(node ->
				node instanceof ActiveActorDestructible &&
						((ActiveActorDestructible) node).isDestroyed()
		);
	}
	/**
	 * Removes all hearts from the level view to reflect the user's remaining health.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}
	/**
	 * Updates the kill count displayed in the level view.
	 */
	private void updateKillCount() {
		// Update the level view with the current kill count
		levelView.updateKillCount(user.getNumberOfKills());

	}
	/**
	 * Handles logic when the game is won, including stopping the timeline,
	 * displaying a win image, and showing restart options.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		backgroundSound.stop();

		winSound.play();
		showConfettiEffect();
		// Add a delay of 10 seconds before showing the restart options
		new Thread(() -> {
			try {
				Thread.sleep(10000); // Delay in milliseconds
				Platform.runLater(() -> {
					RestartWindow restartWindow = new RestartWindow(stage,this);
					restartWindow.showRestartOptions();
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	/**
	 * Displays a confetti effect on the screen.
	 */
	private void showConfettiEffect() {
		ConfettiEffectManager confettiManager = new ConfettiEffectManager(root, screenWidth, screenHeight);
		confettiManager.showConfettiEffect();
	}
	/**
	 * Handles logic when the game is lost, including stopping the timeline
	 * and displaying a game-over image.
	 */
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		backgroundSound.stop();
		loseSound.play();

		// Generate an explosion effect at the center of the screen
		ExplosionEffect explosionEffect = new ExplosionEffect(root);


		explosionEffect.showExplosionsAcrossScreen(10, screenWidth, screenHeight);

	}
	/**
	 * Gets the user plane in the level.
	 *
	 * @return the {@code UserPlane} object
	 */
	protected UserPlane getUser() {
		return user;
	}
	/**
	 * Gets the root group of the level.
	 *
	 * @return the root {@code Group} object
	 */
	protected Group getRoot() {
		return root;
	}
	/**
	 * Gets the current number of active enemies in the level.
	 *
	 * @return the number of enemies currently present in the level.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}
	/**
	 * Adds an enemy unit to the list of active enemies and the root group.
	 *
	 * @param enemy the enemy unit to add
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}
	/**
	 * Gets the maximum Y position for spawning enemy units.
	 *
	 * @return the maximum Y position for enemies
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}
	/**
	 * Gets the screen width of the level.
	 *
	 * @return the screen width
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}
	/**
	 * Checks if the user plane is destroyed.
	 *
	 * @return {@code true} if the user plane is destroyed, {@code false} otherwise
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the number of currently active enemy units.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}
	/**
	 * Gets the stage associated with the level.
	 *
	 * @return the {@code Stage} object
	 */
	public Stage getStage() {
		return this.stage;
	}

}

