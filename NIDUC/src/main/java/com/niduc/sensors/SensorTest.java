package com.niduc.sensors;

public class SensorTest extends Sensor {
    float test__height;
    @Override
    public float getHeight() {
        return this.test__height;
    }

    public void test__setHeight(float height) {
        this.test__height = height;
    }
}
