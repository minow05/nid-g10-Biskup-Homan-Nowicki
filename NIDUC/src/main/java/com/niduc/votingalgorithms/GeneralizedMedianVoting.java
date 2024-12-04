package com.niduc.votingalgorithms;

import com.niduc.Parameter;
import com.niduc.sensors.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Collections;

public class GeneralizedMedianVoting extends VotingAlgorithm {
    public static final String displayName = "Generalized Median Voting Algorithm";
    public static final String description = "This voting algorithm selects a median value by iteratively removing the two sensor outputs that are farthest apart until a single median value remains.";

    private static final ArrayList<Parameter> parameters = new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) GeneralizedMedianVoting.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(); // No configurable parameters for this algorithm
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        // No configurable parameters for this algorithm
    }

    @Override
    public float vote(ArrayList<Sensor> sensors) {
        ArrayList<Float> sensorOutputs = new ArrayList<>();
        for (Sensor sensor : sensors) {
            sensorOutputs.add(sensor.getHeight());
        }

        // Continue until one value remains
        while (sensorOutputs.size() > 1) {
            float maxDistance = Float.MIN_VALUE;
            int removeIndex1 = -1;
            int removeIndex2 = -1;

            // Find the pair of outputs with the greatest distance
            for (int i = 0; i < sensorOutputs.size(); i++) {
                for (int j = i + 1; j < sensorOutputs.size(); j++) {
                    float distance = Math.abs(sensorOutputs.get(i) - sensorOutputs.get(j));
                    if (distance > maxDistance) {
                        maxDistance = distance;
                        removeIndex1 = i;
                        removeIndex2 = j;
                    }
                }
            }

            // Remove the two outputs with the greatest distance
            if (removeIndex1 > removeIndex2) {
                sensorOutputs.remove(removeIndex1);
                sensorOutputs.remove(removeIndex2);
            } else {
                sensorOutputs.remove(removeIndex2);
                sensorOutputs.remove(removeIndex1);
            }
        }

        // Return the final remaining value
        try {
            return sensorOutputs.getFirst();
        } catch (NoSuchElementException e) {
            System.err.println("GeneralizedMedianVoting.vote() called without any sensor outputs.");
            return Float.MIN_VALUE; // Default value in case of error
        }
    }
}