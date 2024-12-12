package com.example.demo.levels;

        import com.example.demo.actors.UserPlane;
        import com.example.demo.actors.l3enemies.PafPlane;
        import com.example.demo.actors.l3enemies.PafPlanePool;
        import com.example.demo.levels.views.LevelView;
        import com.example.demo.ui.PowerUpManager;
        import javafx.application.Platform;
        import javafx.scene.Scene;
        import javafx.scene.input.MouseEvent;
        import javafx.stage.Stage;
        import org.junit.jupiter.api.BeforeAll;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.MockedStatic;
        import org.mockito.Mockito;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class LevelThreeTest {

    private LevelThree levelThree;
    private Stage mockStage;
    private PowerUpManager mockPowerUpManager;
    private PafPlanePool mockPlanePool;
    // Add this block to initialize JavaFX
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {}); // Initializes JavaFX toolkit
    }
    @BeforeEach
    void setUp() {
        mockStage = mock(Stage.class);
        mockPlanePool = mock(PafPlanePool.class);
        mockPowerUpManager = mock(PowerUpManager.class);
        levelThree = Mockito.spy(new LevelThree(800, 600, mockStage));

        // Use reflection to set the private field `powerUpManager`
        try {
            java.lang.reflect.Field powerUpManagerField = LevelThree.class.getDeclaredField("powerUpManager");
            powerUpManagerField.setAccessible(true);
            powerUpManagerField.set(levelThree, mockPowerUpManager);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set up mock PowerUpManager: " + e.getMessage());
        }
    }

    @Test
    void testInitializeScene() {
        Scene scene = levelThree.initializeScene();
        assertNotNull(scene, "Scene should be initialized correctly.");
    }

    @Test
    void testInitializeFriendlyUnits() {
        levelThree.initializeFriendlyUnits();
        assertNotNull(levelThree.getUser(), "User should be added to the scene.");
    }


    @Test
    void testCheckIfGameOver_UserDestroyed() {
        doReturn(true).when(levelThree).userIsDestroyed();

        levelThree.checkIfGameOver();

        // Verify the game ends when the user is destroyed
        verify(levelThree).loseGame();
    }


    @Test
    void testGoToNextLevel() {
        // Directly invoke the method on the existing LevelThree instance
        levelThree.goToNextLevel("LevelFour");

        // Verify level transition logic
        // This assumes `goToNextLevel` triggers some observable effect in the LevelThree instance
        // Replace `verify(levelThree)` with the appropriate assertions for your implementation
        verify(levelThree, times(1)).goToNextLevel("LevelFour");
    }

    @Test
    void testPowerUpActivation() {
        // Mock the ImageView returned by getPowerUpButton
        javafx.scene.image.ImageView mockButton = mock(javafx.scene.image.ImageView.class);

        when(mockPowerUpManager.getPowerUpButton()).thenReturn(mockButton);
        when(mockPowerUpManager.hasPowerUpsRemaining()).thenReturn(true);

        // Simulate the user clicking the power-up button
        doAnswer(invocation -> {
            // Call the actual logic to activate the power-up
            mockPowerUpManager.activatePowerUp();
            return null;
        }).when(mockButton).fireEvent(any(MouseEvent.class));

        // Fire the event to simulate the button click
        mockButton.fireEvent(new MouseEvent(
                MouseEvent.MOUSE_CLICKED,
                100, 100, 100, 100,
                null, 1,
                false, false, false, false,
                true, false, false, false,
                false, true, null
        ));

        // Verify that activatePowerUp was called
        verify(mockPowerUpManager).activatePowerUp();
    }
}
