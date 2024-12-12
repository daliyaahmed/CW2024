package com.example.demo.actors.l4enemies;

import com.example.demo.actors.*;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code L4EnemyFactory} class is responsible for spawning and managing enemies for level 4 of the game.
 * It handles enemy creation, collisions, and win condition checks.
 */
public class L4EnemyFactory {

    /**
     * Count of BlueJets killed by the player.
     */
    private int blueJetsKilled = 0;

    /**
     * Count of WhiteJets killed by the player.
     */
    private int whiteJetsKilled = 0;

    /**
     * Count of GreenJets killed by the player.
     */
    private int greenJetsKilled = 0;

    /**
     * Flag indicating whether BlueJets can be spawned.
     */
    private boolean canSpawnBlueJets = true;

    /**
     * Flag indicating whether WhiteJets can be spawned.
     */
    private boolean canSpawnWhiteJets = true;

    /**
     * Flag indicating whether GreenJets can be spawned.
     */
    private boolean canSpawnGreenJets = true;

    /**
     * Upper bound for enemy vertical movement.
     */
    private static final double UPPER_BOUND = 50;

    /**
     * Lower bound for enemy vertical movement.
     */
    private static final double LOWER_BOUND = 300;

    /**
     * Maximum number of enemies allowed on the screen simultaneously.
     */
    private static final int MAX_ENEMIES_ON_SCREEN = 5;

    /**
     * The root {@code Group} for adding enemies to the scene.
     */
    private final Group root;

    /**
     * The user's plane.
     */
    private final UserPlane user;

    /**
     * List of user projectiles.
     */
    private final List<ActiveActorDestructible> userProjectiles;

    /**
     * List of active enemy units.
     */
    private final List<ActiveActorDestructible> enemyUnits;

    /**
     * List of enemy projectiles.
     */
    private final List<ActiveActorDestructible> enemyProjectiles;

    /**
     * Flag indicating whether the level is complete.
     */
    private boolean isLevelComplete = false;

    /**
     * Constructs an {@code L4EnemyFactory} with the specified user plane, projectiles, and root group.
     *
     * @param user the user's plane
     * @param userProjectiles the list of user projectiles
     * @param enemyUnits the list of enemy units
     * @param enemyProjectiles the list of enemy projectiles
     * @param root the root group for adding enemy nodes
     */
    public L4EnemyFactory(UserPlane user,
                          List<ActiveActorDestructible> userProjectiles,
                          List<ActiveActorDestructible> enemyUnits,
                          List<ActiveActorDestructible> enemyProjectiles,
                          Group root) {
        this.user = user;
        this.userProjectiles = userProjectiles;
        this.enemyUnits = enemyUnits;
        this.enemyProjectiles = enemyProjectiles;
        this.root = root;
    }

    /**
     * Spawns enemies if the maximum number of enemies on the screen has not been reached.
     *
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public void spawnEnemies(double screenWidth, double screenHeight) {
        if (enemyUnits.size() < MAX_ENEMIES_ON_SCREEN) {
            double initialXPos = screenWidth - 300;
            double initialYPos = UPPER_BOUND + Math.random() * (LOWER_BOUND - UPPER_BOUND);

            // Randomly select enemy type, ensuring only allowed types are spawned
            int enemyType = selectEnemyType();

            switch (enemyType) {
                case 0 -> spawnBlueJet(initialXPos, initialYPos);
                case 1 -> spawnWhiteJet(initialXPos, initialYPos);
                case 2 -> spawnGreenJet(initialXPos, initialYPos);
            }
        }
    }

    /**
     * Selects a random enemy type from the allowed types.
     *
     * @return an integer representing the enemy type; -1 if no types are available
     */
    private int selectEnemyType() {
        List<Integer> allowedTypes = new ArrayList<>();
        if (canSpawnBlueJets) allowedTypes.add(0);
        if (canSpawnWhiteJets) allowedTypes.add(1);
        if (canSpawnGreenJets) allowedTypes.add(2);

        if (allowedTypes.isEmpty()) {
            return -1; // No enemy type available
        }

        // Randomly pick from the allowed types
        return allowedTypes.get((int) (Math.random() * allowedTypes.size()));
    }

    /**
     * Checks if the level is complete.
     *
     * @return {@code true} if the level is complete; {@code false} otherwise
     */
    public boolean isLevelComplete() {
        return isLevelComplete;
    }

    /**
     * Spawns a {@code BlueJet} enemy.
     *
     * @param initialXPos the initial X-coordinate of the enemy
     * @param initialYPos the initial Y-coordinate of the enemy
     */
    private void spawnBlueJet(double initialXPos, double initialYPos) {
        if (canSpawnBlueJets) {
            BlueJet blueJet = new BlueJet(initialXPos, initialYPos);
            blueJet.setMoveUpDownBounds(UPPER_BOUND, LOWER_BOUND);
            root.getChildren().add(blueJet);
            enemyUnits.add(blueJet);
        }
    }

    /**
     * Spawns a {@code WhiteJet} enemy.
     *
     * @param initialXPos the initial X-coordinate of the enemy
     * @param initialYPos the initial Y-coordinate of the enemy
     */
    private void spawnWhiteJet(double initialXPos, double initialYPos) {
        if (canSpawnWhiteJets) {
            WhiteJet whiteJet = new WhiteJet(initialXPos, initialYPos);
            whiteJet.setMoveUpDownBounds(UPPER_BOUND, LOWER_BOUND);
            root.getChildren().add(whiteJet);
            enemyUnits.add(whiteJet);
        }
    }

    /**
     * Spawns a {@code GreenJet} enemy.
     *
     * @param initialXPos the initial X-coordinate of the enemy
     * @param initialYPos the initial Y-coordinate of the enemy
     */
    private void spawnGreenJet(double initialXPos, double initialYPos) {
        if (canSpawnGreenJets) {
            GreenJet greenJet = new GreenJet(initialXPos, initialYPos);
            greenJet.setMoveUpDownBounds(UPPER_BOUND, LOWER_BOUND);
            root.getChildren().add(greenJet);
            enemyUnits.add(greenJet);
        }
    }

    /**
     * Handles collisions between user projectiles and enemies.
     */
    public void handleCollisions() {
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handleEnemyCollisionsWithUser();
    }

    /**
     * Handles collisions between user projectiles and enemies, registering kills and removing destroyed entities.
     */
    private void handleUserProjectileCollisions() {
        for (ActiveActorDestructible projectile : new ArrayList<>(userProjectiles)) {
            for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
                if (projectile.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.takeDamage();
                    projectile.takeDamage();

                    if (enemy.isDestroyed()) {
                        registerKill(enemy);
                        root.getChildren().remove(enemy);
                        enemyUnits.remove(enemy);
                    }

                    root.getChildren().remove(projectile);
                    userProjectiles.remove(projectile);
                    break;
                }
            }
        }
    }

    /**
     * Registers a kill for a specific enemy type and updates spawn permissions.
     *
     * @param enemy the enemy that was destroyed
     */
    public void registerKill(ActiveActorDestructible enemy) {
        enemy.destroy(); // Ensure the destroy method is called

        if (enemy instanceof BlueJet) {
            blueJetsKilled++;
            if (blueJetsKilled == 1) {
                canSpawnBlueJets = false; // Stop spawning BlueJets
            }
        } else if (enemy instanceof WhiteJet) {
            whiteJetsKilled++;
            if (whiteJetsKilled == 1) {
                canSpawnWhiteJets = false; // Stop spawning WhiteJets
            }
        } else if (enemy instanceof GreenJet) {
            greenJetsKilled++;
            if (greenJetsKilled == 1) {
                canSpawnGreenJets = false; // Stop spawning GreenJets
            }
        }
        checkWinCondition();
    }

    /**
     * Handles collisions between enemy projectiles and the user.
     */
    private void handleEnemyProjectileCollisions() {
        for (ActiveActorDestructible projectile : new ArrayList<>(enemyProjectiles)) {
            if (projectile.getBoundsInParent().intersects(user.getBoundsInParent())) {
                user.takeDamage();
                projectile.takeDamage();
            }
        }
    }

    /**
     * Handles collisions between enemies and the user's plane.
     */
    private void handleEnemyCollisionsWithUser() {
        for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
            if (enemy.getBoundsInParent().intersects(user.getBoundsInParent())) {
                user.takeDamage();
                enemy.destroy();
                root.getChildren().remove(enemy);
                enemyUnits.remove(enemy);
            }
        }
    }

    /**
     * Checks if the win condition for the level has been met.
     * The win condition requires specific counts of killed enemies.
     */
    private void checkWinCondition() {
        if (greenJetsKilled >= 1 && whiteJetsKilled >= 1 && blueJetsKilled >= 1) {
            isLevelComplete = true;
            System.out.println("Win condition met! Green: " + greenJetsKilled +
                    ", White: " + whiteJetsKilled + ", Blue: " + blueJetsKilled);
        }
    }

    /**
     * getter method to check if Blue Jets can be spawned or not
     * @return canSpawnBlueJets
     */
    public boolean getSpawnBlueJets() {
        return canSpawnBlueJets;
    }


}
