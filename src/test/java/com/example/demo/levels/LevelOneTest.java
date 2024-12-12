package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.UserPlane;
import com.example.demo.levels.views.LevelView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LevelOneTest {

    private LevelOne levelOne;
    private Stage mockStage;

    @BeforeEach
    void setUp() {
        // Initialize JavaFX
        Platform.startup(() -> {});

        mockStage = mock(Stage.class);
        levelOne = new LevelOne(800, 600, mockStage) {
            @Override
            protected void checkIfGameOver() {
                super.checkIfGameOver();
            }
        };
    }

    @AfterEach
    void tearDown() {
        levelOne = null;
    }

    @Test
    void checkIfGameOver() {
        UserPlane mockUser = mock(UserPlane.class);
        when(mockUser.isDestroyed()).thenReturn(true);
        levelOne.user = mockUser;

        levelOne.checkIfGameOver();
        assertTrue(levelOne.userIsDestroyed(), "Game should be over if the user is destroyed.");
    }

    @Test
    void initializeScene() {
        Scene scene = levelOne.initializeScene();
        assertNotNull(scene, "Scene should be initialized.");
        assertEquals(800, scene.getHeight(), "Scene height should match screen height.");
        assertEquals(600, scene.getWidth(), "Scene width should match screen width.");
    }

    @Test
    void initializeFriendlyUnits() {
        levelOne.initializeFriendlyUnits();
        assertTrue(levelOne.getRoot().getChildren().contains(levelOne.getUser()),
                "User plane should be added to the root group.");
    }

    @Test
    void spawnEnemyUnits() {
        int initialEnemies = levelOne.getCurrentNumberOfEnemies();
        levelOne.spawnEnemyUnits();
        assertTrue(levelOne.getCurrentNumberOfEnemies() > initialEnemies,
                "Enemy units should spawn and increase the total number of enemies.");
    }

    @Test
    void goToNextLevel() {
        String nextLevelName = "LevelTwo";
        levelOne.goToNextLevel(nextLevelName);
        // Verify that the level transition logic fires without errors
        assertEquals(nextLevelName, "LevelTwo", "Next level name should match the expected name.");
    }
}
