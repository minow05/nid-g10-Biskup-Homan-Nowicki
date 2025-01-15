package com.niduc;

public class InputSignal {
    float height;
    public void updateHeight(int time){
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
    public float getHeight() {
        return height;
    }
}
