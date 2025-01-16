package com.niduc.sensors;

import com.niduc.Parameter;
import com.niduc.SimulationController;
import com.niduc.errormodels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LidarSensor extends Sensor {
    public static final String displayName = "LIDAR Sensor";
    public static final String description = "LIDAR sensor for measuring distance using laser.";
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
        return new LidarSensor();
    }

    @Override
    public Float getHeight() {
        return this.calculateAppliedErrors(SimulationController.getInputSignal().getHeight());
    }
}
