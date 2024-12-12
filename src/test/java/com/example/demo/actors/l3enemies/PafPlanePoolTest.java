package com.example.demo.actors.l3enemies;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class PafPlanePoolTest {

    private PafPlanePool planePool;
    private final double screenWidth = 0.0;

    @BeforeEach
    void setUp() {
        Platform.startup(() -> {}); // Initializes JavaFX toolkit
        planePool = new PafPlanePool(screenWidth);
    }

    @Test
    void testGetPlaneWhenNoAvailablePlanes() {
        double initialYPos = 0.0;
        PafPlane plane = planePool.getPlane(initialYPos);

        assertNotNull(plane, "Plane should not be null when retrieved from pool.");
        assertEquals(screenWidth, plane.getX(), "Plane's X position should match screen width.");
        assertEquals(initialYPos, plane.getY(), "Plane's Y position should match the initial Y position.");
        assertEquals(1, planePool.getInUsePlanes().size(), "In-use planes should contain the retrieved plane.");
    }

    @Test
    void testGetPlaneWhenAvailablePlanesExist() {
        double initialYPos1 = 100.0;
        double initialYPos2 = 200.0;

        PafPlane plane1 = planePool.getPlane(initialYPos1);
        planePool.releasePlane(plane1);

        PafPlane plane2 = planePool.getPlane(initialYPos2);

        assertSame(plane1, plane2, "The same plane instance should be reused from the pool.");
        assertEquals(screenWidth, plane2.getX(), "Plane's X position should match screen width.");
        assertEquals(initialYPos2, plane2.getY(), "Plane's Y position should be updated correctly.");
    }

    @Test
    void testReleasePlane() {
        double initialYPos = 100.0;
        PafPlane plane = planePool.getPlane(initialYPos);

        planePool.releasePlane(plane);

        assertTrue(planePool.getInUsePlanes().isEmpty(), "In-use planes list should be empty after release.");
        assertEquals(1, planePool.getAvailablePlanes().size(), "Available planes list should contain the released plane.");
        assertTrue(plane.isDestroyed(), "Released plane should be marked as destroyed.");
    }

    @Test
    void testMultiplePlanesInUse() {
        double initialYPos1 = 100.0;
        double initialYPos2 = 200.0;

        PafPlane plane1 = planePool.getPlane(initialYPos1);
        PafPlane plane2 = planePool.getPlane(initialYPos2);

        List<PafPlane> inUsePlanes = planePool.getInUsePlanes();

        assertEquals(2, inUsePlanes.size(), "In-use planes list should contain both planes.");
        assertTrue(inUsePlanes.contains(plane1), "In-use planes list should contain the first plane.");
        assertTrue(inUsePlanes.contains(plane2), "In-use planes list should contain the second plane.");
    }

    @Test
    void testPlaneResetOnReuse() {
        double initialYPos1 = 100.0;
        double initialYPos2 = 200.0;

        PafPlane plane1 = planePool.getPlane(initialYPos1);
        planePool.releasePlane(plane1);

        PafPlane reusedPlane = planePool.getPlane(initialYPos2);

        assertFalse(reusedPlane.isDestroyed(), "Reused plane should not be marked as destroyed.");
        assertEquals(initialYPos2, reusedPlane.getY(), "Reused plane's Y position should be updated correctly.");
    }
}
