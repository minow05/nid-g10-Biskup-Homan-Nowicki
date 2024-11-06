package com.niduc;

public abstract class Sensor {
//PUBIC APSTRAKT KLAS
    private float standardDeviation;
    public abstract float getHeight();

    public float getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(float standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
}
