package com.example.demo.physics;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.UserPlane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code Collision} class handles collision detection and resolution between various entities in the game,
 * including the player, friendly units, enemy units, and projectiles.
 */
public class Collision {
    private final UserPlane user;
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    private long lastDamageTime = 0;
    private static final long DAMAGE_COOLDOWN = 2000; // 2 seconds
    private final double screenWidth;

    /**
     * Constructs a {@code Collision} manager for handling interactions between game entities.
     *
     * @param user            the {@code UserPlane} representing the player
     * @param friendlyUnits   the list of friendly {@code ActiveActorDestructible} units
     * @param enemyUnits      the list of enemy {@code ActiveActorDestructible} units
     * @param userProjectiles the list of projectiles fired by the user
     * @param enemyProjectiles the list of projectiles fired by enemies
     * @param screenWidth     the width of the screen for boundary checks
     */
    public Collision(
            UserPlane user,
            List<ActiveActorDestructible> friendlyUnits,
            List<ActiveActorDestructible> enemyUnits,
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyProjectiles,
            double screenWidth
    ) {
        this.user = user;
        this.friendlyUnits = friendlyUnits;
        this.enemyUnits = enemyUnits;
        this.userProjectiles = userProjectiles;
        this.enemyProjectiles = enemyProjectiles;
        this.screenWidth = screenWidth;
    }

    /**
     * Handles collisions between friendly units and enemy units.
     */
    public void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     * Destroys enemies when hit and increments the user's kill count.
     */
    public void handleUserProjectileCollisions() {
        for (ActiveActorDestructible projectile : userProjectiles) {
            for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
                if (projectile.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.takeDamage();
                    projectile.takeDamage();

                    if (enemy.isDestroyed()) {
                        user.incrementKillCount();
                    }
                }
            }
        }
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     */
    public void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Generic method to handle collisions between two lists of {@code ActiveActorDestructible} entities.
     *
     * @param actors1 the first list of entities
     * @param actors2 the second list of entities
     */
    private void handleCollisions(
            List<ActiveActorDestructible> actors1,
            List<ActiveActorDestructible> actors2
    ) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    /**
     * Handles enemy penetration beyond the player's defenses. Damages the user
     * and destroys the enemy if penetration occurs.
     *
     * @return {@code true} if the user's health is fully depleted, otherwise {@code false}
     */
    public boolean handleEnemyPenetration() {
        long currentTime = System.currentTimeMillis();
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                if (currentTime - lastDamageTime > DAMAGE_COOLDOWN) {
                    user.takeDamage();
                    lastDamageTime = currentTime;
                }
                enemy.destroy();
            }
        }
        return user.getHealth() <= 0;
    }

    /**
     * Checks if an enemy has moved beyond the screen boundaries, indicating penetration.
     *
     * @param enemy the {@code ActiveActorDestructible} enemy to check
     * @return {@code true} if the enemy has penetrated, otherwise {@code false}
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    /**
     * Removes all destroyed entities from a given list.
     *
     * @param actors the list of {@code ActiveActorDestructible} entities to filter
     */
    public void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .collect(Collectors.toList());
        actors.removeAll(destroyedActors);
    }
}
