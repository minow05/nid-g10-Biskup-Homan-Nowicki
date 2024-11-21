package com.niduc.votingalgorithms;

import com.niduc.Parameter;
import com.niduc.sensors.Sensor;

import java.util.ArrayList;
import java.util.Map;

public abstract class VotingAlgorithm {
    public static final String displayName = "Some idiot didn't override it in subclass";
    public static final String description = "Some idiot didn't override it in subclass";

    /**
     * Return list of assignable parameters for this Voting Algorithm
     * @return list of assignable parameters
     */
    public abstract ArrayList<Parameter> getParameters();

    /**
     * Returns list of current values of assignable parameters for this Voting Algorithm
     * @return values of assignable parameters
     */
    public abstract Map<String, Object> getParameterValues();

    /**
     * Sets values of assignable parameters for this Voting Algorithm
     * @param parameters map of parameter IDs and values to be assigned
     */
    public abstract void setParameterValues(Map<String, Object> parameters);

    /**
     * Sets value of an assignable parameter for this Voting Algorithm
     * @param parameterID parameter ID
     * @param value value to set
     */
    public void setParameterValue(String parameterID, Object value) {
        this.setParameterValues(Map.of(parameterID, value));
    }

    /**
     * Votes for the best value to return based on list of sensor outputs
     * @param sensors list of sensors to vote from
     * @return voted best value
     */
    public abstract float vote(ArrayList<Sensor> sensors);
}
