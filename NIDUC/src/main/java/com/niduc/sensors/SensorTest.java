package com.niduc.sensors;

import com.niduc.SimulationController;
import com.niduc.errormodels.ErrorModel;

public class SensorTest extends Sensor {
    float test__height;
    ErrorModel errorModel;
    @Override
    public float getHeight() {
        return errorModel.getErrorValue(SimulationController.getInputSignal().getHeight());
    }

    public void setErrorModel(ErrorModel errorModel) {
        this.errorModel = errorModel;
    }
}
