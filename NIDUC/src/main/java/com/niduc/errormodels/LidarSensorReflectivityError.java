package com.niduc.errormodels;

import com.niduc.Parameter;
import com.niduc.SimulationController;

import java.util.ArrayList;
import java.util.Map;

public class LidarSensorReflectivityError extends ErrorModel {
    public static final String displayName = "Reflectivity Error (LIDAR)";
    public static final String description = "Simulates errors due to reflectivity of surfaces affecting LIDAR measurements.";

    private float reflectivity = 0.5f;
    private float intensity = 1.0f;

    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("reflectivity", Float.class, "Set reflectivity of the surface (0 to 1)"));
        add(new Parameter("intensity", Float.class, "Set intensity of the reflectivity error"));
    }};

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) LidarSensorReflectivityError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "reflectivity", this.reflectivity,
                "intensity", this.intensity
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("reflectivity")) {
            try {
                this.reflectivity = (float) parameters.get("reflectivity");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'ReflectivityError.reflectivity'");
            }
        }
        if (parameters.containsKey("intensity")) {
            try {
                this.intensity = (float) parameters.get("intensity");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'ReflectivityError.intensity'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        float height = SimulationController.getInputSignal().getHeight();
        float error = reflectivity * intensity * (height / 100.0f);
        return inputValue + error;
    }

    @Override
    public ErrorModel getNewInstance() {
        return new LidarSensorReflectivityError();
    }
}