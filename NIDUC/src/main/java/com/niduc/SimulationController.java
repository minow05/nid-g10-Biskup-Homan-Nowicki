package com.niduc;

import com.niduc.sensors.Sensor;
import com.niduc.votingalgorithms.*;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class SimulationController {
    private static int time = 0;
    private static int simulationFramerate = 100;
    private static long lastFrame = 0;
    private static boolean isRunning = false;
    private static Float votedValue;

    public static final ArrayList<VotingAlgorithm> votingAlgorithms = new ArrayList<>() {{
        add(new FormalizedMajorityVoting());
        add(new GeneralizedMedianVoting());
        add(new WeightedAveragingAlgorithm());
    }};
    private static VotingAlgorithm currentVotingAlgorithm;
    private static ArrayList<Sensor> sensors = new ArrayList<>();

    private static MainViewController mainViewController;
    private static InputSignal inputSignal;

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
        SimulationController.inputSignal.updateHeight(SimulationController.time);
        System.out.println("Sensors in time: " + SimulationController.time);
        for (Sensor sensor : SimulationController.sensors) {
            System.out.println(sensor.getHeight());
        }
        SimulationController.votedValue = SimulationController.currentVotingAlgorithm.vote(SimulationController.sensors);
        SimulationController.mainViewController.update();
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

    public static Float getVotedValue() {
        return votedValue;
    }

    public static void setMainViewController(MainViewController mainViewController) {
        SimulationController.mainViewController = mainViewController;
    }

    public static int getTime() {
        return time;
    }

    public static void setInputSignal(InputSignal inputSignal) {
        SimulationController.inputSignal = inputSignal;
    }
    public static InputSignal getInputSignal() {
        return inputSignal;
    }
}
