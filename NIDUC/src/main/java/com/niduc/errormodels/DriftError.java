package com.niduc.errormodels;

import com.niduc.Parameter;
import com.niduc.SimulationController;

import java.util.ArrayList;
import java.util.Map;


public class DriftError extends ErrorModel {
    public static final String displayName = "Sensor Drift Error";
    public static final String description = "Simulates a gradual drift in the sensor reading over time";

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    private float driftRate = 0.1f;
    private float maxDrift = 10f;
    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("driftRate", Float.class, "Rate of drift (feet per second)"));
        add(new Parameter("maxDrift", Float.class, "Maximum allowed drift (feet)"));
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
        return inputValue + Math.max(Math.min(SimulationController.getTime() * driftRate, maxDrift), -maxDrift);
    }

    @Override
    public ErrorModel getNewInstance() {
        return new DriftError();
    }
}
