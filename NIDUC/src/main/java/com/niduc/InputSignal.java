package com.niduc;

public class InputSignal {
    float height;
    public void updateHeight(int time){
        if (time >= 0 && time < 30) {
            this.height = 2 * (float) Math.pow(time, 2);
        } else if (time >= 30 && time < 300) {
            this.height = 1800 + 50 * (time - 30);
        } else {
            this.height = 12000;
        }
    }
    public float getHeight() {
        return height;
    }
}
