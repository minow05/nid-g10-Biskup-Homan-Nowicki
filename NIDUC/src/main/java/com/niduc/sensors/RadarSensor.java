package com.niduc.sensors;

import com.niduc.Parameter;
import com.niduc.SimulationController;
import com.niduc.errormodels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RadarSensor extends Sensor {
    public static final String displayName = "Radar Sensor";
    public static final String description = "Radar sensor for measuring distance using radio waves.";

    private float radarAccuracy;
    private static final ArrayList<Parameter> parameters = new ArrayList<>(
            List.of(
                    new Parameter("radarAccuracy", Float.class, "Accuracy of the Radar sensor")
            )
    );

    private final List<Class<? extends ErrorModel>> allowedErrors = List.of(
            RandomNoiseError.class,
            OscillatingError.class,
            DriftError.class,
            IntermittentDropoutError.class
    );

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ArrayList<Parameter> getParameters() {
        return new ArrayList<>(parameters);
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of("radarAccuracy", radarAccuracy);
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("radarAccuracy")) {
            try {
                this.radarAccuracy = (float) parameters.get("radarAccuracy");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'radarAccuracy'.");
            }
        }
    }

    @Override
    public Sensor getNewInstance() {
        return new RadarSensor();
    }

    @Override
    public float getHeight() {
        return this.calculateAppliedErrors(SimulationController.getInputSignal().getHeight());
    }

    @Override
    public void addError(ErrorModel error) {
        if (!allowedErrors.contains(error.getClass())) {
            throw new IllegalArgumentException("This error model is not allowed for Radar Sensor.");
        }
        super.addError(error);
    }
}
