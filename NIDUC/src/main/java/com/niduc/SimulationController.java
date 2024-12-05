package com.niduc;

import com.niduc.sensors.Sensor;
import com.niduc.votingalgorithms.ConsensusVoting;
import com.niduc.votingalgorithms.VotingAlgorithm;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class SimulationController {
    private static int time = 0;
    private static int simulationFramerate = 100;
    private static long lastFrame = 0;
    private static boolean isRunning = false;

    public static final ArrayList<VotingAlgorithm> votingAlgorithms = new ArrayList<>() {{
        add(new ConsensusVoting());
    }};
    private static VotingAlgorithm currentVotingAlgorithm;
    private static ArrayList<Sensor> sensors;

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
        SimulationController.sensors = new ArrayList<>();
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

    public static ArrayList<Sensor> getSensors() {
        return SimulationController.sensors;
    }

    public static void addSensor(Sensor sensor) {
        SimulationController.sensors.add(sensor);
    }

    public static VotingAlgorithm getCurrentVotingAlgorithm() {
        return currentVotingAlgorithm;
    }

    public static void setCurrentVotingAlgorithm(VotingAlgorithm currentVotingAlgorithm) {
        SimulationController.currentVotingAlgorithm = currentVotingAlgorithm;
    }
}
