package com.niduc.votingalgorithms;

import com.niduc.Parameter;
import com.niduc.sensors.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class WeightedAveragingAlgorithm extends VotingAlgorithm {
    public static final String displayName = "Weighted Averaging Algorithm";
    public static final String description = "This algorithm calculates a weighted average of sensor outputs, with weights inversely proportional to distances between outputs.";

    // Default parameter values
    private float scalingConstant = 1.0f;

    // Define parameters as a static list
    private static final ArrayList<Parameter> parameters = new ArrayList<>(
            List.of(
                    new Parameter("scalingConstant", Float.class, "Scaling constant for the algorithm")
            )
    );

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) WeightedAveragingAlgorithm.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "scalingConstant", this.scalingConstant
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("scalingConstant")) {
            try {
                this.scalingConstant = (float) parameters.get("scalingConstant");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'WeightedAveragingAlgorithm.scalingConstant'.");
            }
        }
    }

    @Override
    public float vote(ArrayList<Sensor> sensors) {
        if (sensors.isEmpty()) {
            throw new IllegalArgumentException("No sensors provided for voting.");
        }

        int n = sensors.size();
        ArrayList<Float> sensorOutputs = new ArrayList<>();
        for (Sensor sensor : sensors) {
            sensorOutputs.add(sensor.getHeight());
        }

        float[] weights = new float[n];
        float weightSum = 0;

        for (int i = 0; i < n; i++) {
            float distanceSum = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    distanceSum += Math.abs(sensorOutputs.get(i) - sensorOutputs.get(j));
                }
            }
            weights[i] = 1.0f / (distanceSum + scalingConstant);
            weightSum += weights[i];
        }

        for (int i = 0; i < n; i++) {
            weights[i] /= weightSum;
        }

        float result = 0;
        for (int i = 0; i < n; i++) {
            result += weights[i] * sensorOutputs.get(i);
        }

        return result;
    }
}
