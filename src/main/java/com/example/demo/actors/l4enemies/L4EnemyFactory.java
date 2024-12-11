package com.example.demo.actors.l4enemies;

import com.example.demo.actors.*;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class L4EnemyFactory {

    private int blueJetsSpawned = 0;
    private int blueJetsKilled = 0;

    private int whiteJetsSpawned = 0;
    private int whiteJetsKilled = 0;

    private boolean greenJetSpawned = false;
    private boolean greenJetKilled = false;
    private static final double UPPER_BOUND = 50;  // Upper bound for enemy movement
    private static final double LOWER_BOUND = 300; // Lower bound for enemy movement
    private static final int MAX_ENEMIES_ON_SCREEN = 5;

    private final UserPlane user;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final Group root;

    private int greenJetsKilled = 0;
    private boolean isLevelComplete = false;

    private boolean canSpawnBlueJets = true;
    private boolean canSpawnWhiteJets = true;
    private boolean canSpawnGreenJets = true;


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
    public boolean isLevelComplete() {
        return isLevelComplete;
    }

    private void spawnBlueJet(double initialXPos, double initialYPos) {
        if (canSpawnBlueJets) {
            BlueJet blueJet = new BlueJet(initialXPos, initialYPos);
            blueJet.setMoveUpDownBounds(UPPER_BOUND, LOWER_BOUND);
            root.getChildren().add(blueJet);
            enemyUnits.add(blueJet);
        }
    }

    private void spawnWhiteJet(double initialXPos, double initialYPos) {
        if (canSpawnWhiteJets) {
            WhiteJet whiteJet = new WhiteJet(initialXPos, initialYPos);
            whiteJet.setMoveUpDownBounds(UPPER_BOUND, LOWER_BOUND);
            root.getChildren().add(whiteJet);
            enemyUnits.add(whiteJet);
        }
    }

    private void spawnGreenJet(double initialXPos, double initialYPos) {
        if (canSpawnGreenJets) {
            GreenJet greenJet = new GreenJet(initialXPos, initialYPos);
            greenJet.setMoveUpDownBounds(UPPER_BOUND, LOWER_BOUND);
            root.getChildren().add(greenJet);
            enemyUnits.add(greenJet);
        }
    }

    public void handleCollisions() {
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handleEnemyCollisionsWithUser();
    }

    private void handleUserProjectileCollisions() {
        for (ActiveActorDestructible projectile : new ArrayList<>(userProjectiles)) {
            for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
                if (projectile.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.takeDamage();
                    projectile.takeDamage();

                    if (enemy.isDestroyed()) {
                        registerKill(enemy);
                        System.out.println("Enemy destroyed: " + enemy.getClass().getSimpleName());
                        root.getChildren().remove(enemy);
                        enemyUnits.remove(enemy); // Ensure it is removed from the game state
                        System.out.println("Removing enemy after destryed after user projectile destryed: " + enemy);
                    }

                    root.getChildren().remove(projectile);
                    userProjectiles.remove(projectile);
                    break;
                }
            }
        }
    }
    public void registerKill(ActiveActorDestructible enemy) {
        if (enemy instanceof BlueJet) {
            blueJetsKilled++;
            System.out.println("BlueJet killed 1st: " + blueJetsKilled);
            if (blueJetsKilled == 1) {
                canSpawnBlueJets = false; // Stop spawning BlueJets

            }
            System.out.println("BlueJet killed: " + blueJetsKilled);
        } else if (enemy instanceof WhiteJet) {
            whiteJetsKilled++;
            System.out.println("WhiteJet killed 1st: " + whiteJetsKilled);
            if (whiteJetsKilled == 1) {
                canSpawnWhiteJets = false; // Stop spawning WhiteJets

            }
            System.out.println("WhiteJet killed: " + whiteJetsKilled);
        } else if (enemy instanceof GreenJet) {
            greenJetsKilled++;
            System.out.println("GreenJet killed 1st: " + greenJetsKilled);
            if (greenJetsKilled == 1) {
                canSpawnGreenJets = false; // Stop spawning GreenJets

            }
            System.out.println("GreenJet killed: " + greenJetsKilled);
        }
        checkWinCondition();
        System.out.println("Remaining enemies: " + enemyUnits.size());
        System.out.println("Remaining projectiles: " + userProjectiles.size());
    }

    private void handleEnemyProjectileCollisions() {
        for (ActiveActorDestructible projectile : new ArrayList<>(enemyProjectiles)) {
            if (projectile.getBoundsInParent().intersects(user.getBoundsInParent())) {
                user.takeDamage();
                projectile.takeDamage();
            }
        }
    }

    private void handleEnemyCollisionsWithUser() {
        for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
            if (enemy.getBoundsInParent().intersects(user.getBoundsInParent())) {
                user.takeDamage();
                enemy.destroy();
                root.getChildren().remove(enemy);
                enemyUnits.remove(enemy);
                System.out.println("Removing enemy: " + enemy);
                System.out.println("Remaining enemies: " + enemyUnits.size());
                //checkWinCondition(); // Check if the win condition is met
            }
        }
    }

    private void handleBlueJetCollision(BlueJet blueJet, ActiveActorDestructible projectile) {
        blueJet.takeDamage();
        projectile.takeDamage();


        if (blueJet.isDestroyed()) {
            registerKill(blueJet);
            root.getChildren().remove(blueJet);
            enemyUnits.remove(blueJet);
            System.out.println("Removing enemy: " + blueJet);
            System.out.println("Remaining enemies: " + enemyUnits.size());
            System.out.println("BlueJet destroyed!");
        }

        root.getChildren().remove(projectile);
        userProjectiles.remove(projectile);
        System.out.println("Projectile removed: " + projectile);
    }

    private void handleWhiteJetCollision(WhiteJet whiteJet, ActiveActorDestructible projectile) {
        whiteJet.takeDamage();
        projectile.takeDamage();

        if (whiteJet.isDestroyed()) {
            registerKill(whiteJet);
            root.getChildren().remove(whiteJet);
            enemyUnits.remove(whiteJet);
            System.out.println("Removing enemy: " + whiteJet);
            System.out.println("Remaining enemies: " + enemyUnits.size());
            System.out.println("WhiteJet destroyed!");
        }

        root.getChildren().remove(projectile);
        userProjectiles.remove(projectile);
        System.out.println("Projectile removed: " + projectile);
    }

    private void handleGreenJetCollision(GreenJet greenJet, ActiveActorDestructible projectile) {
        greenJet.takeDamage();
        projectile.takeDamage();

        if (greenJet.isDestroyed()) {
            registerKill(greenJet);
            root.getChildren().remove(greenJet);
            enemyUnits.remove(greenJet);
            System.out.println("Removing enemy: " + greenJet);
            System.out.println("Remaining enemies: " + enemyUnits.size());
            System.out.println("GreenJet destroyed!");
        }

        root.getChildren().remove(projectile);
        userProjectiles.remove(projectile);
        System.out.println("Projectile removed: " + projectile);
    }
    private void checkWinCondition() {
        // Win condition: 2 GreenJets, 3 WhiteJets, and 4 BlueJets killed
        if (greenJetsKilled >= 1 && whiteJetsKilled >= 1 && blueJetsKilled >= 1) {
            isLevelComplete = true;
            System.out.println("Win condition met!");
            System.out.println("Win condition met! Green: " + greenJetsKilled +
                    ", White: " + whiteJetsKilled + ", Blue: " + blueJetsKilled);
        }
    }

}
