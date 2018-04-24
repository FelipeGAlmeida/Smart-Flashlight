package com.fgapps.servicetest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by (Engenharia) Felipe on 03/02/2018.
 */

public class ProxSensorListener implements SensorEventListener {

    private boolean flashable;

    public boolean isFlashable() {
        return flashable;
    }

    public ProxSensorListener(){
        //Constructor
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float response = sensorEvent.values[0];
        if(response < 5){
            flashable = false;
        }else{
            flashable = true;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
