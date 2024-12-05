package com.niduc.errormodels;

import com.niduc.Parameter;

import java.util.ArrayList;
import java.util.Map;


public class DriftError extends ErrorModel {
    public static final String displayName = "Sensor Drift Error";
    public static final String description = "Simulates a gradual drift in the sensor reading over time";

    private float driftRate = 0.1f; // Default drift rate (meters per second)
    private float maxDrift = 10f;   // Maximum drift allowed
    private float accumulatedDrift = 0f; // Tracks the current drift
    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("driftRate", Float.class, "Rate of drift (meters per second)"));
        add(new Parameter("maxDrift", Float.class, "Maximum allowed drift (meters)"));
    }};


    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) DriftError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "driftRate", this.driftRate,
                "maxDrift", this.maxDrift
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("driftRate")) {
            try {
                this.driftRate = (float) parameters.get("driftRate");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'DriftError.driftRate'");
            }
        }
        if (parameters.containsKey("maxDrift")) {
            try {
                this.maxDrift = (float) parameters.get("maxDrift");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'DriftError.maxDrift'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        // Increment the accumulated drift by the drift rate, but cap it at maxDrift
        accumulatedDrift = Math.min(accumulatedDrift + driftRate, maxDrift);
        // Apply the drift to the input value
        return inputValue + accumulatedDrift;
    }
}
