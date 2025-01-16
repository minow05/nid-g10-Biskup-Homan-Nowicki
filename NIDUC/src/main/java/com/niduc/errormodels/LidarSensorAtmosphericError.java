package com.niduc.errormodels;

import com.niduc.Parameter;
import com.niduc.SimulationController;

import java.util.ArrayList;
import java.util.Map;

public class LidarSensorAtmosphericError extends ErrorModel {
    public static final String displayName = "Atmospheric Error (LIDAR)";
    public static final String description = "Simulates errors due to atmospheric conditions like dust or fog affecting LIDAR measurements.";

    private float intensity = 1.0f;

    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("intensity", Float.class, "Set intensity of the atmospheric error"));
    }};

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) LidarSensorAtmosphericError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "intensity", this.intensity
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("intensity")) {
            try {
                this.intensity = (float) parameters.get("intensity");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'AtmosphericError.intensity'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        float time = SimulationController.getTime();
        float error = intensity * (float) Math.sin(time / 10.0);
        return inputValue + error;
    }

    @Override
    public ErrorModel getNewInstance() {
        return new LidarSensorAtmosphericError();
    }
}
