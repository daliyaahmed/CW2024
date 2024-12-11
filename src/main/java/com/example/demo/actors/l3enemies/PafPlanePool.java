package com.example.demo.actors.l3enemies;

import com.example.demo.actors.l3enemies.PafPlane;

import java.util.ArrayList;
import java.util.List;

public class PafPlanePool {

    private final List<PafPlane> availablePlanes = new ArrayList<>();
    private final List<PafPlane> inUsePlanes = new ArrayList<>();

    private final double screenWidth;

    public PafPlanePool(double screenWidth) {
        this.screenWidth = screenWidth;
    }

    public PafPlane getPlane(double initialYPos) {
        PafPlane plane;

        if (availablePlanes.isEmpty()) {
            plane = new PafPlane(screenWidth, initialYPos);
        } else {
            plane = availablePlanes.remove(0);
            plane.setPosition(screenWidth, initialYPos);
            plane.notDestroy();
        }

        inUsePlanes.add(plane);
        return plane;
    }

    public void releasePlane(PafPlane plane) {
        plane.destroy();
        inUsePlanes.remove(plane);
        availablePlanes.add(plane);
    }

    public List<PafPlane> getInUsePlanes() {
        return inUsePlanes;
    }
}
