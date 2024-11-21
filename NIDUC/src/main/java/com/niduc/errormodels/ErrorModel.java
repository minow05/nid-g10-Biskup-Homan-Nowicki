package com.niduc.errormodels;

import com.niduc.Parameter;

import java.util.ArrayList;
import java.util.Map;

public abstract class ErrorModel {
    public static final String displayName = "Some idiot didn't override it in subclass";
    public static final String description = "Some idiot didn't override it in subclass";

    /**
     * Return list of assignable parameters for this Error Model
     * @return list of assignable parameters
     */
    public abstract ArrayList<Parameter> getParameters();

    /**
     * Returns list of current values of assignable parameters for this Error Model
     * @return values of assignable parameters
     */
    public abstract Map<String, Object> getParameterValues();

    /**
     * Sets values of assignable parameters for this Error Model
     * @param parameters map of parameter IDs and values to be assigned
     */
    public abstract void setParameterValues(Map<String, Object> parameters);

    /**
     * Sets value of an assignable parameter for this Error Model
     * @param parameterID parameter ID
     * @param value value to set
     */
    public void setParameterValue(String parameterID, Object value) {
        this.setParameterValues(Map.of(parameterID, value));
    }

    /**
     * Returns input value with error based on this Error Model
     * @param inputValue sensor input
     * @return value with error
     */
    public abstract float getErrorValue(float inputValue);
}
