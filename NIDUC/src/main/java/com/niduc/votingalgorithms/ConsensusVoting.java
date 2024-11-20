package com.niduc.votingalgorithms;

import com.niduc.Parameter;
import com.niduc.sensors.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsensusVoting extends VotingAlgorithm {
    public static final String displayName = "Consensus Voting Algorithm";

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
        ArrayList<Float> sensorOutputs = new ArrayList<>();
        ArrayList<Float> sensorOutputsPartitions = new ArrayList<>();
        for (Sensor sensor : sensors) {
            sensorOutputs.add(sensor.getHeight());
        }
        // TODO: Finish vote method
        return 0f;
    }
}
