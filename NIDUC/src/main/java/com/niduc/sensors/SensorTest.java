package com.niduc.sensors;

import com.niduc.Parameter;
import com.niduc.SimulationController;
import com.niduc.errormodels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SensorTest extends Sensor {
    public static final String displayName = "Test Sensor";
    public static final String description = "Sensor for testing purposes";

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    float test__height;
    float test__param;

    private static final ArrayList<Parameter> parameters = new ArrayList<>(
            List.of(
                    new Parameter("testParam", Float.class, "Test parameter")
            )
    );

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) parameters.clone();
    }

    @Override
    public ArrayList<ErrorModel> getAllowedErrors() {
        return SimulationController.errorModels;
    }


    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "testParam", this.test__param
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("testParam")) {
            try {
                this.test__param = (float) parameters.get("testParam");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'testParam'.");
            }
        }
    }

    @Override
    public Sensor getNewInstance() {
        return new SensorTest();
    }

    @Override
    public Float getHeight() {
        return this.calculateAppliedErrors(SimulationController.getInputSignal().getHeight());
    }
    public void test__setHeight(float height) {
        this.test__height = height;
    }
}
