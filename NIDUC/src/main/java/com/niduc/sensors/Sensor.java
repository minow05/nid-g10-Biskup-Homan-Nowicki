package com.niduc.sensors;

import com.niduc.Parameter;
import com.niduc.errormodels.ErrorModel;

import java.util.ArrayList;
import java.util.Map;

public abstract class Sensor {
    public abstract String getDisplayName();
    public abstract String getDescription();

    private final ArrayList<ErrorModel> appliedErrors = new ArrayList<>();

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

    public void addError(ErrorModel error) {
        this.appliedErrors.add(error);
    }

    public void removeError(int index) {
        this.appliedErrors.remove(index);
    }

    public void moveErrorUp(int index) {
        if (index <= 0)
            return;
        ErrorModel movedError = this.appliedErrors.get(index);
        ErrorModel previousError = this.appliedErrors.get(index - 1);
        this.appliedErrors.set(index - 1, movedError);
        this.appliedErrors.set(index, previousError);
    }

    public void moveErrorDown(int index) {
        if (index >= this.appliedErrors.size() - 1)
            return;
        ErrorModel movedError = this.appliedErrors.get(index);
        ErrorModel nextError = this.appliedErrors.get(index + 1);
        this.appliedErrors.set(index + 1, movedError);
        this.appliedErrors.set(index, nextError);
    }

    public ArrayList<ErrorModel> getAppliedErrors() {
        return appliedErrors;
    }

    public abstract Sensor getNewInstance();

    public abstract float getHeight();

    public String getDisplayHeight() {
        return String.valueOf(getHeight()) + " ft";
    }

    protected float calculateAppliedErrors(float inputValue) {
        float result = inputValue;
        for (ErrorModel error : this.appliedErrors)
            result = error.getErrorValue(result);
        return result;
    }

    public String toString() { return this.getDisplayName(); }
}
