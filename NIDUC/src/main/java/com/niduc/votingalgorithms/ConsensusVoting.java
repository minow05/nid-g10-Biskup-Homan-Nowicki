package com.niduc.votingalgorithms;

import com.niduc.Parameter;
import com.niduc.sensors.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ConsensusVoting extends VotingAlgorithm {
    public static final String displayName = "Formalized Majority Voting Algorithm";
    public static final String description = "This voting algorithm groups similar outputs together based on a set closeness threshold. It selects the largest group, and if it has more than half of all outputs, any result from this group can be chosen as the consensus answer.";

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    private float allowedDifference = 10f;
    private static final ArrayList<Parameter> parameters = new ArrayList<>(
            List.of(
                    new Parameter("allowedDifference", Float.class, "Allowed difference")
            )
    );

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) ConsensusVoting.parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "allowedDifference", this.allowedDifference
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("allowedDifference")) {
            try {
                this.allowedDifference = (float) parameters.get("allowedDifference");
            }
            catch (ClassCastException e){
                System.err.println("Invalid value for parameter 'ConsensusVoting.allowedDifference'");
            }
        }
    }

    @Override
    public float vote(ArrayList<Sensor> sensors) {
        float votedValue = Float.MIN_VALUE;
        ArrayList<Float> sensorOutputs = new ArrayList<>();
        ArrayList<Float> currentSensorOutputsPartition = new ArrayList<>();
        ArrayList<Float> largestSensorOutputsPartition = new ArrayList<>();
        for (Sensor sensor : sensors) {
            sensorOutputs.add(sensor.getHeight());
        }
        while (!sensorOutputs.isEmpty()) {
            currentSensorOutputsPartition.add(sensorOutputs.getFirst());
            sensorOutputs.removeFirst();
            ArrayList<Float> sensorOutputsToRemove = new ArrayList<>();
            for (Float sensorOutput : sensorOutputs) {
                boolean outputInAllowedDifference = true;
                for (Float value : currentSensorOutputsPartition) {
                    if(Math.abs(sensorOutput - value) > allowedDifference) {
                        outputInAllowedDifference = false;
                        break;
                    }
                }
                if (outputInAllowedDifference) {
                    currentSensorOutputsPartition.add(sensorOutput);
                    sensorOutputsToRemove.add(sensorOutput);
                }
            }
            sensorOutputs.removeAll(sensorOutputsToRemove);
            if (currentSensorOutputsPartition.size() > largestSensorOutputsPartition.size())
                largestSensorOutputsPartition = currentSensorOutputsPartition;
            currentSensorOutputsPartition = new ArrayList<>();
        }

        try {
            votedValue = largestSensorOutputsPartition.getFirst();
        }
        catch (NoSuchElementException e) {
            System.err.println("ConsensusVoting.vote() called without an element in the list.");
        }

        return votedValue;
    }
}
