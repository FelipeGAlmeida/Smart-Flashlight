package com.fgapps.servicetest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

/**
 * Created by (Engenharia) Felipe on 03/02/2018.
 */

public class LightSensorListener implements SensorEventListener {

    Context context;
    ProxSensorListener proxSensorListener;
    private SensorManager mSensorManager;
    private Sensor mProxSensor;

    public LightSensorListener(Context context){
        this.context = context;
        proxSensorListener = new ProxSensorListener();
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mProxSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(proxSensorListener,mProxSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float response = sensorEvent.values[0];
        if(response>300) response = 300;
        if(context instanceof MainActivity)
            ((MainActivity) context).getLightLevel().setProgress((int)response);
        if (response < 10 && proxSensorListener.isFlashable()) {
            flashLightOn();
        }else {
            flashLightOff();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void flashLightOn() {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
        }
    }

    public void flashLightOff() {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
        }
    }
}
