package com.example.demo.actors.l4enemies;

import com.example.demo.actors.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class L4EnemyFactoryTest {

    private L4EnemyFactory enemyFactory;
    private UserPlane mockUser;
    private List<ActiveActorDestructible> mockUserProjectiles;
    private List<ActiveActorDestructible> mockEnemyUnits;
    private List<ActiveActorDestructible> mockEnemyProjectiles;
    private Group mockRoot;

    @BeforeEach
    void setUp() {
        Platform.startup(() -> {}); // Initializes JavaFX toolkit
        mockUser = mock(UserPlane.class);
        mockUserProjectiles = new ArrayList<>();
        mockEnemyUnits = new ArrayList<>();
        mockEnemyProjectiles = new ArrayList<>();
        mockRoot = mock(Group.class);

        enemyFactory = new L4EnemyFactory(mockUser, mockUserProjectiles, mockEnemyUnits, mockEnemyProjectiles, mockRoot);
        // Inside the setUp method
        ObservableList mockChildren = FXCollections.observableArrayList();
        when(mockRoot.getChildren()).thenReturn(mockChildren);
    }

    @AfterEach
    void tearDown() {
        enemyFactory = null;
    }

    @Test
    void spawnEnemies() {
        double screenWidth = 800.0;
        double screenHeight = 600.0;

        // Call the method under test
        enemyFactory.spawnEnemies(screenWidth, screenHeight);

        // Verify the interaction with the mockRoot
        verify(mockRoot, atLeastOnce()).getChildren();
        assertFalse(mockEnemyUnits.isEmpty(), "Enemy units should not be empty after spawning enemies.");
    }


    @Test
    void isLevelComplete() {
        // Initially, the level should not be complete
        assertFalse(enemyFactory.isLevelComplete(), "Level should not be complete initially.");

        // Simulate kills to meet win condition
        enemyFactory.registerKill(mock(BlueJet.class));
        enemyFactory.registerKill(mock(WhiteJet.class));
        enemyFactory.registerKill(mock(GreenJet.class));

        assertTrue(enemyFactory.isLevelComplete(), "Level should be complete after meeting win conditions.");
    }





    @Test
    void registerKill() {
        ActiveActorDestructible mockEnemy = mock(BlueJet.class);

        // Simulate a destroyed state for the mock enemy
        when(mockEnemy.isDestroyed()).thenReturn(true);

        // Register a kill
        enemyFactory.registerKill(mockEnemy);

        // Verify the destroy method is invoked
        verify(mockEnemy).destroy();

        // Verify spawning is disabled for BlueJets after one kill
        assertFalse(enemyFactory.getSpawnBlueJets(), "BlueJets spawning should be disabled after one kill.");
    }

}
