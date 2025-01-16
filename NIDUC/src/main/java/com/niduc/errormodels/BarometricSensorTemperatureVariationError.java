package com.niduc.errormodels;

import com.niduc.Parameter;
import com.niduc.SimulationController;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class BarometricSensorTemperatureVariationError extends ErrorModel {
    public static final String displayName = "Temperature Variation Error (Barometric)";
    public static final String description = "Simulates errors in pressure-to-height conversions due to temperature changes.";

    private float groundTemperature = 15f;
    private float randomness = 1f;
    private float errorCalculationCoefficient = 0.5f;

    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("groundTemperature", Float.class, "Ground temperature in Celsius"));
        add(new Parameter("randomness", Float.class, "Randomness factor for temperature variation"));
        add(new Parameter("errorCalculationCoefficient", Float.class, "Temperature influence on reading"));
    }};

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) BarometricSensorTemperatureVariationError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "groundTemperature", this.groundTemperature,
                "randomness", this.randomness,
                "errorCalculationCoefficient", this.errorCalculationCoefficient
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("groundTemperature")) {
            try {
                this.groundTemperature = (float) parameters.get("groundTemperature");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'TemperatureVariationError.groundTemperature'");
            }
        }
        if (parameters.containsKey("randomness")) {
            try {
                this.randomness = (float) parameters.get("randomness");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'TemperatureVariationError.randomness'");
            }
        }
        if (parameters.containsKey("errorCalculationCoefficient")) {
            try {
                this.errorCalculationCoefficient = (float) parameters.get("errorCalculationCoefficient");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'TemperatureVariationError.errorCalculationCoefficient'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        float height = SimulationController.getInputSignal().getHeight();
        float temperatureAtHeight = groundTemperature - (height / 1000f * 6.5f);
        float randomFactor = new Random().nextFloat() * randomness * 2 - randomness;
        float effectiveTemperature = temperatureAtHeight + randomFactor;
        float temperatureEffect = effectiveTemperature * this.errorCalculationCoefficient;
        return inputValue + temperatureEffect;
    }

    @Override
    public ErrorModel getNewInstance() {
        return new BarometricSensorTemperatureVariationError();
    }
}
