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
    public static final ArrayList<ErrorModel> allowedErrors = new ArrayList<>() {{
        add(new BiasError());
        add(new ConstantValueError());
        add(new DriftError());
        add(new IntermittentDropoutError());
        add(new OscillatingError());
        add(new RandomNoiseError());
    }};

    private static final ArrayList<Parameter> parameters = new ArrayList<>();

    @Override
    public ArrayList<ErrorModel> getAllowedErrors() {
        return allowedErrors;
    }

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
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of();
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
    }

    @Override
    public Sensor getNewInstance() {
        return new RadarSensor();
    }

    @Override
    public Float getHeight() {
        float height = SimulationController.getInputSignal().getHeight();
        if (height > 2800)
            return null;
        return this.calculateAppliedErrors(SimulationController.getInputSignal().getHeight());
    }
}
