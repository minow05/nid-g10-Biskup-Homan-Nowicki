package com.niduc.errormodels;

import com.niduc.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConstantValueError extends ErrorModel{
    public static final String displayName = "Constant Value Error";
    public static final String description = "Assigns constant reading to the sensor based on 'Constant value' parameter";

    private float constantValue = 10f;
    private static final ArrayList<Parameter> parameters = new ArrayList<>(
            List.of(
                    new Parameter("constantValue", Float.class, "Constant value")
            )
    );

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) ConstantValueError.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "constantValue", this.constantValue
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("constantValue")) {
            try {
                this.constantValue = (float) parameters.get("constantValue");
            }
            catch (ClassCastException e){
                System.err.println("Invalid value for parameter 'ConstantValueError.constantValue'");
            }
        }
    }

    @Override
    public float getErrorValue(float inputValue) {
        return this.constantValue;
    }
}
