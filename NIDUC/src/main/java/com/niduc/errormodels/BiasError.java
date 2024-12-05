package com.niduc.errormodels;

import com.niduc.Parameter;

import java.util.ArrayList;
import java.util.Map;

public class BiasError extends ErrorModel {
    public static final String displayName = "Bias Error";
    public static final String description = "Simulates a constant offset in the sensor reading";

    private float biasValue = 10f; // Default bias value
    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("biasValue", Float.class, "Constant offset added to the sensor reading"));
    }};

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) BiasError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "biasValue", this.biasValue
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("biasValue")) {
            try {
                this.biasValue = (float) parameters.get("biasValue");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'BiasError.biasValue'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        // Apply the bias to the input value
        return inputValue + biasValue;
    }
}