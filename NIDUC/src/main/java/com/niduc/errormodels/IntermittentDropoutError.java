package com.niduc.errormodels;

import com.niduc.Parameter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class IntermittentDropoutError extends ErrorModel {
    public static final String displayName = "Intermittent Dropout Error";
    public static final String description = "Simulates intermittent loss of sensor readings, outputting zero occasionally";

    private float dropoutProbability = 0.1f; // Default probability of dropout (10%)
    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("dropoutProbability", Float.class, "Probability of sensor dropout (0.0 - 1.0)"));
    }};

    private final Random random = new Random();

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) IntermittentDropoutError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "dropoutProbability", this.dropoutProbability
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("dropoutProbability")) {
            try {
                this.dropoutProbability = (float) parameters.get("dropoutProbability");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'IntermittentDropoutError.dropoutProbability'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        // Simulate dropout based on probability
        if (random.nextFloat() < dropoutProbability) {
            return 0f; // Dropout: return zero as the sensor reading
        }
        return inputValue; // No dropout: return the input value unchanged
    }
}