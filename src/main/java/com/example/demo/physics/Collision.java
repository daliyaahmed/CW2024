
package com.example.demo.physics;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.UserPlane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Collision {
    private final UserPlane user;
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    private long lastDamageTime = 0;
    private static final long DAMAGE_COOLDOWN = 2000; // 2 seconds
    private final double screenWidth;

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

    public void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    public void handleUserProjectileCollisions() {
        for (ActiveActorDestructible projectile : userProjectiles) {
            for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
                if (projectile.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.takeDamage();
                    projectile.takeDamage();

                    // Check if the enemy is destroyed by the projectile
                    if (enemy.isDestroyed()) {
                        user.incrementKillCount();
                    }
                }
            }
        }
    }

    public void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

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

        // Check if user health has fully depleted
        return user.getHealth() <= 0;
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    public void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .collect(Collectors.toList());
        actors.removeAll(destroyedActors);
    }
}
