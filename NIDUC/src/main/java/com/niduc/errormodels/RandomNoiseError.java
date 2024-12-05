package com.niduc.errormodels;

import com.niduc.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomNoiseError extends ErrorModel {
    public static final String displayName = "Random Noise Error";
    public static final String description = "Adds random noise to the sensor reading based on 'Noise range' parameter";

    private float noiseRange = 5f; // Default noise range
    private static final ArrayList<Parameter> parameters = new ArrayList<>(
            List.of(
                    new Parameter("noiseRange", Float.class, "Maximum amplitude of random noise")
            )
    );

    private final Random random = new Random();

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) RandomNoiseError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "noiseRange", this.noiseRange
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("noiseRange")) {
            try {
                this.noiseRange = (float) parameters.get("noiseRange");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'RandomNoiseError.noiseRange'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        // Generate random noise in the range [-noiseRange, noiseRange]
        float noise = (random.nextFloat() * 2 - 1) * noiseRange;
        return inputValue + noise;
    }
}