package com.fgapps.servicetest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

public class ApplicationService extends Service {

    private SensorManager mSensorManager;
    private Sensor mLightSensor;

    public static boolean isRunning;
    private static final int CAMERA_REQUEST = 50;
    private boolean hasCameraFlash = false;

    private LightSensorListener lightSensorListener;
    private ProxSensorListener proxSensorListener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String pref = MainActivity.manageInfo(getApplicationContext(), null, 1);
        if(pref.equals(MainActivity.ON)){
        initListeners();
        initSensors();
        }else{
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initListeners() {
        lightSensorListener = new LightSensorListener(this);
    }

    private void initSensors() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(lightSensorListener,mLightSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onCreate() {
        isRunning = true;
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }
}
