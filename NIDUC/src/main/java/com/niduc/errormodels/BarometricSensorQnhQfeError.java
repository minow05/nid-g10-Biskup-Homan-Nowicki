package com.niduc.errormodels;

import com.niduc.Parameter;
import com.niduc.SimulationController;

import java.util.ArrayList;
import java.util.Map;

public class BarometricSensorQnhQfeError extends ErrorModel {
    public static final String displayName = "QNH/QFE Error (Barometric)";
    public static final String description = "Simulates errors due to incorrect QNH or QFE settings, where QNH is height relative to sea level and QFE is height relative to set local height (most often airfield height)";

    private boolean useQnh = true;
    private float qfeHeight = 100f;

    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("useQnh", Boolean.class, "Toggle between QNH (true) and QFE (false)"));
        add(new Parameter("qfeHeight", Boolean.class, "Set QFE altitude in feet"));
    }};

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) BarometricSensorQnhQfeError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "useQnh", this.useQnh,
                "qfeHeight", this.qfeHeight
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("useQnh")) {
            try {
                this.useQnh = (boolean) parameters.get("useQnh");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'QnhQfeError.useQnh'");
            }
        }
        if (parameters.containsKey("qfeHeight")) {
            try {
                this.qfeHeight = (float) parameters.get("qfeHeight");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'QnhQfeError.qfeHeight'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        float height = SimulationController.getInputSignal().getHeight();
        return useQnh ? inputValue : inputValue - qfeHeight;
    }

    @Override
    public ErrorModel getNewInstance() {
        return new BarometricSensorQnhQfeError();
    }
}
