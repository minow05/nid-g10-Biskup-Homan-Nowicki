package com.niduc.sensors;

import com.niduc.Parameter;
import com.niduc.SimulationController;
import com.niduc.errormodels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarometricSensor extends Sensor {
    public static final String displayName = "Barometric Sensor";
    public static final String description = "Barometric sensor for measuring atmospheric pressure.";

    private float barometricSensitivity;
    private static final ArrayList<Parameter> parameters = new ArrayList<>(
            List.of(
                    new Parameter("barometricSensitivity", Float.class, "Sensitivity of the Barometric sensor")
            )
    );

    private final List<Class<? extends ErrorModel>> allowedErrors = List.of(
            DriftError.class,
            ConstantValueError.class,
            IntermittentDropoutError.class,
            BiasError.class
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
        return Map.of("barometricSensitivity", barometricSensitivity);
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("barometricSensitivity")) {
            try {
                this.barometricSensitivity = (float) parameters.get("barometricSensitivity");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'barometricSensitivity'.");
            }
        }
    }

    @Override
    public Sensor getNewInstance() {
        return new BarometricSensor();
    }

    @Override
    public float getHeight() {
        return this.calculateAppliedErrors(SimulationController.getInputSignal().getHeight());
    }

    @Override
    public void addError(ErrorModel error) {
        if (!allowedErrors.contains(error.getClass())) {
            throw new IllegalArgumentException("This error model is not allowed for Barometric Sensor.");
        }
        super.addError(error);
    }
}
