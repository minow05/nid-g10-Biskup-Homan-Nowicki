package com.niduc.errormodels;

import com.niduc.Parameter;
import com.niduc.SimulationController;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class RadarSensorSignalAttenuationError extends ErrorModel {
    public static final String displayName = "Signal Attenuation Error (Radar)";
    public static final String description = "Simulates errors caused by rain, snow, or fog attenuating the signal strength, leading to inaccuracies in height detection.";

    private float rainFactor = 0.1f;
    private float snowFactor = 0.2f;
    private float fogFactor = 0.15f;
    private boolean isRainy = false;
    private boolean isSnowy = false;
    private boolean isFoggy = false;

    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("rainFactor", Float.class, "Attenuation factor for rain (0-1)"));
        add(new Parameter("snowFactor", Float.class, "Attenuation factor for snow (0-1)"));
        add(new Parameter("fogFactor", Float.class, "Attenuation factor for fog (0-1)"));
        add(new Parameter("isRainy", Boolean.class, "Is it raining?"));
        add(new Parameter("isSnowy", Boolean.class, "Is it snowing?"));
        add(new Parameter("isFoggy", Boolean.class, "Is it foggy?"));
    }};

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) RadarSensorSignalAttenuationError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "rainFactor", this.rainFactor,
                "snowFactor", this.snowFactor,
                "fogFactor", this.fogFactor,
                "isRainy", this.isRainy,
                "isSnowy", this.isSnowy,
                "isFoggy", this.isFoggy
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("rainFactor")) this.rainFactor = (float) parameters.get("rainFactor");
        if (parameters.containsKey("snowFactor")) this.snowFactor = (float) parameters.get("snowFactor");
        if (parameters.containsKey("fogFactor")) this.fogFactor = (float) parameters.get("fogFactor");
        if (parameters.containsKey("isRainy")) this.isRainy = (boolean) parameters.get("isRainy");
        if (parameters.containsKey("isSnowy")) this.isSnowy = (boolean) parameters.get("isSnowy");
        if (parameters.containsKey("isFoggy")) this.isFoggy = (boolean) parameters.get("isFoggy");
    }

    @Override
    public float getErrorValue(float inputValue) {
        float attenuation = 1.0f;

        if (isRainy) attenuation -= new Random().nextFloat() * rainFactor;
        if (isSnowy) attenuation -= new Random().nextFloat() * snowFactor;
        if (isFoggy) attenuation -= new Random().nextFloat() * fogFactor;

        return inputValue * Math.max(attenuation, 0.5f);
    }

    @Override
    public ErrorModel getNewInstance() {
        return new RadarSensorSignalAttenuationError();
    }
}