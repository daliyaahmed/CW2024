package com.example.demo.controller;

import com.example.demo.levels.LevelParent;
import com.example.demo.ui.FullScreenHandler;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.beans.PropertyChangeEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    @Mock
    private Stage mockStage;

    @Mock
    private FullScreenHandler mockFullScreenHandler;

    @Mock
    private LevelParent mockLevel;

    private Controller controller;

    @BeforeEach
    void setUp() {
        Platform.startup(() -> {}); // Initializes JavaFX toolkit
        MockitoAnnotations.openMocks(this);
        when(mockStage.getHeight()).thenReturn(600.0);
        when(mockStage.getWidth()).thenReturn(800.0);
        controller = new Controller(mockStage);
    }

    @Test
    void launchGame() throws Exception {
        // Mock FullScreenHandler behavior
        doNothing().when(mockFullScreenHandler).setFullScreen();
        doNothing().when(mockFullScreenHandler).enableFullScreenMode();

        // Verify FullScreenHandler interactions
        controller.launchGame();
        verify(mockStage).show();
    }

    @Test
    void propertyChange() throws Exception {
        // Mock behavior of property change event
        PropertyChangeEvent event = new PropertyChangeEvent(this, "level", null, "com.example.demo.levels.LevelTwo");

        // Simulate property change
        controller.propertyChange(event);

        // Capture and verify method calls
        ArgumentCaptor<String> classNameCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockStage).setScene(any(Scene.class));
    }
}
