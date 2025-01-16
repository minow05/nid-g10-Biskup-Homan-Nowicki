package com.niduc.errormodels;

import com.niduc.Parameter;
import com.niduc.SimulationController;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class RadarSensorSurfaceTypeError extends ErrorModel {
    public static final String displayName = "Surface Type Error (Radar)";
    public static final String description = "Simulates errors caused by reflective or absorptive surface types, such as water or uneven terrain, affecting signal accuracy.";

    private float waterReflectionFactor = 0.9f;
    private float unevenTerrainFactor = 1.1f;
    private boolean isOverWater = false;
    private boolean isOverUnevenTerrain = false;

    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("waterReflectionFactor", Float.class, "Reflection factor for water surfaces (0-1)"));
        add(new Parameter("unevenTerrainFactor", Float.class, "Error factor for uneven terrain (0-1)"));
        add(new Parameter("isOverWater", Boolean.class, "Is the radar over water?"));
        add(new Parameter("isOverUnevenTerrain", Boolean.class, "Is the radar over uneven terrain?"));
    }};

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) RadarSensorSurfaceTypeError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "waterReflectionFactor", this.waterReflectionFactor,
                "unevenTerrainFactor", this.unevenTerrainFactor,
                "isOverWater", this.isOverWater,
                "isOverUnevenTerrain", this.isOverUnevenTerrain
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("waterReflectionFactor")) this.waterReflectionFactor = (float) parameters.get("waterReflectionFactor");
        if (parameters.containsKey("unevenTerrainFactor")) this.unevenTerrainFactor = (float) parameters.get("unevenTerrainFactor");
        if (parameters.containsKey("isOverWater")) this.isOverWater = (boolean) parameters.get("isOverWater");
        if (parameters.containsKey("isOverUnevenTerrain")) this.isOverUnevenTerrain = (boolean) parameters.get("isOverUnevenTerrain");
    }

    @Override
    public float getErrorValue(float inputValue) {
        float height = SimulationController.getInputSignal().getHeight();
        float attenuation = 1.0f;

        if (isOverWater) attenuation += new Random().nextFloat() * waterReflectionFactor;
        if (isOverUnevenTerrain) attenuation -= new Random().nextFloat() * unevenTerrainFactor;

        return inputValue * Math.max(attenuation, 0.5f);
    }

    @Override
    public ErrorModel getNewInstance() {
        return new RadarSensorSurfaceTypeError();
    }
}
