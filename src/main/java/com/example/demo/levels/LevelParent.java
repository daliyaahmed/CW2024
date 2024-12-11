package com.example.demo.levels;

//import java.applet.AudioClip;
import java.util.*;

import com.example.demo.physics.Collision;
import com.example.demo.levels.views.LevelView;
import com.example.demo.levels.views.LevelViewLevelFour;
import com.example.demo.levels.views.LevelViewLevelThree;
import com.example.demo.menus.PauseMenuState;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.UserPlane;
import com.example.demo.menus.RestartWindow;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

//observer addition import



public abstract class LevelParent  {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	//updated code for better enemy penetration
	//added two variables lastDamageTime and DAMAGE_COOLDOWN to avoid system errors while playing the game
	private long lastDamageTime = 0;
	private static final long DAMAGE_COOLDOWN = 2000; // 2 seconds
	//until here
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
    private final AudioClip fireSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/shoot.wav").toExternalForm());

	private final Group root;
	private final Timeline timeline;
	public  UserPlane user;
	private final Scene scene;
	private final ImageView background;
	private final Collision collision;
	// In LevelParent constructor
	private PauseMenuState pauseMenuState;
	// Add a Stage variable to keep track of the main window stage
	// Store the Stage instance in LevelParent
	private final Stage stage;
	public final AudioClip backgroundSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/background.wav").toExternalForm());

	private final List<ActiveActorDestructible> friendlyUnits;
	public final List<ActiveActorDestructible> enemyUnits;
	public final List<ActiveActorDestructible> userProjectiles;
	public final List<ActiveActorDestructible> enemyProjectiles;
	private final List<FighterPlane> fighterPlanes;
	private final LevelViewLevelThree levelViewLevelThree;
	private final LevelViewLevelFour levelViewLevelFour;
	private int currentNumberOfEnemies;
	private final LevelView levelView;
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

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}
	//changed to property change
	public void goToNextLevel(String levelName) {
		pcs.firePropertyChange("level", null, levelName);
		System.out.println("Transitioning to next level: " + levelName);
	}


	//added listener
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	//added remove listener
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
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

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN ) user.stop();
			}
		});
		root.getChildren().add(background);
		// Replace the pause button creation in initializeBackground
		Button pauseButton = pauseMenuState.createPauseButton();
		root.getChildren().add(pauseButton);
	}

	private void fireProjectile() {
		// Play sound effect
		fireSound.play();
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> {
			// Only let FighterPlane enemies generate projectiles
			if (enemy instanceof FighterPlane) {
				FighterPlane fighterPlane = (FighterPlane) enemy;
				spawnEnemyProjectile(fighterPlane.fireProjectile());
			}
		});
	}

	// Add this method to help with LevelView access
	protected LevelView getLevelView() {
		return levelView;
	}
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	protected void addFighterPlane(FighterPlane fighterPlane) {
		fighterPlanes.add(fighterPlane);
		root.getChildren().add(fighterPlane);
	}



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

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		// Update the level view with the current kill count
		levelView.updateKillCount(user.getNumberOfKills());

	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		backgroundSound.stop();
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

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}
	public Stage getStage() {
		return this.stage;
	}

}

