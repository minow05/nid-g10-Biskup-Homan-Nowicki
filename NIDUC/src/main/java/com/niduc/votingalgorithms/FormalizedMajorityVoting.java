package com.niduc.votingalgorithms;

import com.niduc.Parameter;
import com.niduc.sensors.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashSet;

public class FormalizedMajorityVoting extends VotingAlgorithm {
    public static final String displayName = "Formalized Majority Voting Algorithm";
    public static final String description = "This algorithm groups sensor outputs into clusters based on a closeness threshold and selects the output from the largest cluster.";

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }

    private float closenessThreshold = 1.0f;
    private static final ArrayList<Parameter> parameters = new ArrayList<>(
            List.of(
                    new Parameter("closenessThreshold", Float.class, "The closeness threshold to define clusters")
            )
    );

    @Override
    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) parameters.clone();
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return Map.of(
                "closenessThreshold", this.closenessThreshold
        );
    }

    @Override
    public void setParameterValues(Map<String, Object> parameters) {
        if (parameters.containsKey("closenessThreshold")) {
            try {
                this.closenessThreshold = (float) parameters.get("closenessThreshold");
            } catch (ClassCastException e) {
                System.err.println("Invalid value for parameter 'closenessThreshold'.");
            }
        }
    }

    @Override
    public float vote(ArrayList<Sensor> sensors) {
        ArrayList<Float> sensorOutputs = new ArrayList<>();
        for (Sensor sensor : sensors) {
            sensorOutputs.add(sensor.getHeight());
        }

        // Step 1: Partition outputs into clusters based on the closeness threshold
        ArrayList<HashSet<Float>> clusters = new ArrayList<>();

        for (Float output : sensorOutputs) {
            boolean addedToCluster = false;

            // Check each existing cluster
            for (HashSet<Float> cluster : clusters) {
                for (Float clusterOutput : cluster) {
                    if (Math.abs(clusterOutput - output) <= closenessThreshold) {
                        cluster.add(output);
                        addedToCluster = true;
                        break;
                    }
                }
                if (addedToCluster) break;
            }

            // If output doesn't belong to any existing cluster, create a new cluster
            if (!addedToCluster) {
                HashSet<Float> newCluster = new HashSet<>();
                newCluster.add(output);
                clusters.add(newCluster);
            }
        }

        // Step 2: Find the largest cluster
        HashSet<Float> largestCluster = new HashSet<>();
        for (HashSet<Float> cluster : clusters) {
            if (cluster.size() > largestCluster.size()) {
                largestCluster = cluster;
            }
        }

        // Step 3: Select any value from the largest cluster as the output
        if (!largestCluster.isEmpty()) {
            return largestCluster.iterator().next();
        }

        // If no output is found, return a default value
        System.err.println("FormalizedMajorityVoting.vote() called with no valid clusters.");
        return Float.MIN_VALUE;
    }
}
