package com.niduc.errormodels;

import com.niduc.Parameter;
import com.niduc.SimulationController;

import java.util.ArrayList;
import java.util.Map;

public class OscillatingError extends ErrorModel {
    public static final String displayName = "Oscillating Error";
    public static final String description = "Simulates periodic fluctuations in the sensor reading";

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    private float amplitude = 5f; // Default oscillation amplitude
    private float frequency = 1f; // Default oscillation frequency (cycles per second)
    private static final ArrayList<Parameter> parameters = new ArrayList<>() {{
        add(new Parameter("amplitude", Float.class, "Amplitude of the oscillation (meters)"));
        add(new Parameter("frequency", Float.class, "Frequency of the oscillation (cycles per second)"));
    }};

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) OscillatingError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "amplitude", this.amplitude,
                "frequency", this.frequency
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("amplitude")) {
            try {
                this.amplitude = (float) parameters.get("amplitude");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'OscillatingError.amplitude'");
            }
        }
        if (parameters.containsKey("frequency")) {
            try {
                this.frequency = (float) parameters.get("frequency");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'OscillatingError.frequency'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        // Get the current simulation time
        float currentTime = SimulationController.getTime();

        // Calculate the oscillating error using a sine wave
        float oscillation = (float) (amplitude * Math.sin(2 * Math.PI * frequency * currentTime));

        // Add the oscillation to the input value
        return inputValue + oscillation;
    }

    @Override
    public ErrorModel getNewInstance() {
        return new OscillatingError();
    }
}
