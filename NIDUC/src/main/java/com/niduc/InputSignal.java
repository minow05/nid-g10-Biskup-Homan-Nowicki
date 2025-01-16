package com.niduc;

public class InputSignal {
    private float height;
    private boolean isHighAltitudeTest = true;
    public void updateHeight(int time){
        if (isHighAltitudeTest)
            highAltitudeInput(time);
        else
            lowAltitudeInput(time);
    }
    public void highAltitudeInput(int time) {
        if (time >= 0 && time < 800)
            this.height = 45 * time;
        else if (time >= 800 && time < 1000)
            this.height = 36000;
        else if (time >= 1000 && time < 1600)
            this.height = 81000 - 45 * time;
        else if (time >= 1600 && time < 3100)
            this.height = 18600 - 6 * time;
        else
            this.height = 0;
    }
    public void lowAltitudeInput(int time) {
        if (time >= 0 && time < 200)
            this.height = 12.5f * time;
        else if (time >= 200 && time < 500)
            this.height = 2500;
        else if (time >= 500 && time < 700)
            this.height = 8750 - 12.5f * time;
        else
            this.height = 0;
    }
    public float getHeight() {
        return height;
    }
    public boolean isHighAltitudeTest() {
        return isHighAltitudeTest;
    }
    public void setIsHighAltitudeTest(boolean isHighAltitudeTest) {
        this.isHighAltitudeTest = isHighAltitudeTest;
    }
}
