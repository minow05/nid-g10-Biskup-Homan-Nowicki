package com.niduc;

import javafx.animation.AnimationTimer;

public class SimulationController {
    private static int time = 0;
    private static int simulationFramerate = 100;
    private static long lastFrame = 0;
    private static boolean isRunning = false;
    private static MainViewController mainViewController;

    private final static AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if(lastFrame + (1000000000 / simulationFramerate) < now) {
                lastFrame = now;
                SimulationController.update();
            }
        }
    };

    /**
     * Resets simulation and sets up the SimulationController
     */
    public static void setup() {
        SimulationController.time = 0;
    }

    /**
     * Runs simulation
     */
    public static void run() {
        if (SimulationController.isRunning) return;
        SimulationController.isRunning = true;
        animationTimer.start();
    }

    /**
     * Pauses simulation
     */
    public static void stop() {
        if (!SimulationController.isRunning) return;
        SimulationController.isRunning = false;
        animationTimer.stop();
    }

    /**
     * Updates every entity
     */
    private static void update() {
        SimulationController.time++;
        System.out.println(SimulationController.time);
    }

    public static int getSimulationFramerate() {
        return SimulationController.simulationFramerate;
    }

    public static void setSimulationFramerate(int simulationFramerate) {
        SimulationController.simulationFramerate = simulationFramerate;
    }
}
