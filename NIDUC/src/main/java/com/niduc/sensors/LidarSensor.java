package com.niduc.sensors;

import com.niduc.Parameter;
import com.niduc.SimulationController;
import com.niduc.errormodels.ErrorModel;
import com.niduc.errormodels.IntermittentDropoutError;
import com.niduc.errormodels.OscillatingError;
import com.niduc.errormodels.RandomNoiseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LidarSensor extends Sensor {
    public static final String displayName = "LIDAR Sensor";
    public static final String description = "LIDAR sensor for measuring distance using laser.";

    private float lidarRange;
    private static final ArrayList<Parameter> parameters = new ArrayList<>(
            List.of(
                    new Parameter("lidarRange", Float.class, "Range of the LIDAR sensor")
            )
    );

    private final List<Class<? extends ErrorModel>> allowedErrors = List.of(
            RandomNoiseError.class,
            OscillatingError.class,
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
        return Map.of("lidarRange", lidarRange);
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("lidarRange")) {
            try {
                this.lidarRange = (float) parameters.get("lidarRange");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'lidarRange'.");
            }
        }
    }

    @Override
    public Sensor getNewInstance() {
        return new LidarSensor();
    }

    @Override
    public float getHeight() {
        return this.calculateAppliedErrors(SimulationController.getInputSignal().getHeight());
    }

    @Override
    public void addError(ErrorModel error) {
        if (!allowedErrors.contains(error.getClass())) {
            throw new IllegalArgumentException("This error model is not allowed for LIDAR Sensor.");
        }
        super.addError(error);
    }
}
