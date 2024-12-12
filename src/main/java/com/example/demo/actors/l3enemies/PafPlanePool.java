package com.example.demo.actors.l3enemies;
import java.util.ArrayList;
import java.util.List;
/**
 * The {@code PafPlanePool} class manages a pool of {@code PafPlane} objects.
 * It optimizes the reuse of {@code PafPlane} instances to improve performance and memory usage.
 * It is applying the rule of object pooling in refactoring.
 */
public class PafPlanePool {
    /**
     * List of available (not in use) {@code PafPlane} objects.
     */
    private final List<PafPlane> availablePlanes = new ArrayList<>();
    /**
     * List of {@code PafPlane} objects currently in use.
     */
    private final List<PafPlane> inUsePlanes = new ArrayList<>();
    /**
     * The width of the screen, used to position planes.
     */
    private final double screenWidth;
    /**
     * Constructs a {@code PafPlanePool} with the specified screen width.
     *
     * @param screenWidth the width of the screen
     */
    public PafPlanePool(double screenWidth) {
        this.screenWidth = screenWidth;
    }
    /**
     * Retrieves a {@code PafPlane} from the pool, creating a new one if none are available.
     *
     * @param initialYPos the initial Y-coordinate for the plane
     * @return a {@code PafPlane} object positioned at the specified Y-coordinate
     */
    public PafPlane getPlane(double initialYPos) {
        PafPlane plane;

        if (availablePlanes.isEmpty()) {
            plane = new PafPlane(screenWidth, initialYPos);
        } else {
            plane = availablePlanes.remove(0);
            plane.setPosition(screenWidth, initialYPos);
            plane.notDestroy();
        }

        getInUsePlanes().add(plane);
        return plane;
    }
    /**
     * Releases a {@code PafPlane} back into the pool, making it available for reuse.
     *
     * @param plane the {@code PafPlane} to be released
     */
    public void releasePlane(PafPlane plane) {
        plane.destroy();
        inUsePlanes.remove(plane);
        availablePlanes.add(plane);
    }
    /**
     * Retrieves the list of {@code PafPlane} objects currently in use.
     *
     * @return a list of {@code PafPlane} objects in use
     */
    public List<PafPlane> getInUsePlanes() {
        return inUsePlanes;
    }
    /**
     * Retrieves the list of {@code PafPlane} objects available to use.
     *
     * @return a list of {@code PafPlane} objects to use
     */
    public List<PafPlane> getAvailablePlanes(){ return availablePlanes;}
}
