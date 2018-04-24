package com.fgapps.servicetest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private Sensor mProxSensor;
    private Intent appService;

    private ArcProgress lightLevel;
    private Switch switchOnOff;

    private static final int CAMERA_REQUEST = 50;
    private boolean hasCameraFlash = false;

    public static final String LOCAL_INFO = "APP_PREF";
    public static final String ENABLE = "ENABLE";
    public static final String ON = "ON";
    public static final String OFF = "OFF";


    private LightSensorListener lightSensorListener;
    private ProxSensorListener proxSensorListener;

    /**
     * GETTERS AND SETTERS
     */

    public ArcProgress getLightLevel(){
        return lightLevel;
    }

    /**
     * IMPLEMENTATION
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (hasCameraFlash) {
            initListeners();
            initInterfaceObjects();
            requestPermissions();

            boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED;

            appService = new Intent(this, ApplicationService.class);

        } else {
            Toast.makeText(MainActivity.this, "No flash available on your device. Application won't work",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initListeners() {
        lightSensorListener = new LightSensorListener(this);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
    }

    private void initInterfaceObjects() {
        switchOnOff = findViewById(R.id.switchOnOff);
        switchOnOff.setChecked(ApplicationService.isRunning);

        lightLevel = findViewById(R.id.lightLevel_id);
        lightLevel.setBottomText("LUMENS");
        lightLevel.setMax(300);
        lightLevel.setBottomTextSize(60);
        lightLevel.setTextColor(Color.WHITE);
        lightLevel.setTextSize(200);
        lightLevel.setSuffixText("");
        lightLevel.setStrokeWidth(50);
        lightLevel.setFinishedStrokeColor(Color.WHITE);
        lightLevel.setUnfinishedStrokeColor(Color.DKGRAY);
    }

    private void initSensors() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(lightSensorListener,mLightSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case CAMERA_REQUEST :
                if (grantResults.length > 0  &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initSensors();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied for the Camera, application won't work !",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ApplicationService.isRunning) {
            stopService(appService);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(switchOnOff.isChecked()){
            startService(appService);
            manageInfo(getApplicationContext(), ON, 0);
            return;
        }
        manageInfo(getApplicationContext(), OFF, 0);
        return;
    }

    public static String manageInfo(Context c, String enable, int mode){
        SharedPreferences sp = c.getSharedPreferences(LOCAL_INFO, 0);
        SharedPreferences.Editor editor = sp.edit();
        if(mode==0) {
            editor.putString(ENABLE, enable);
            editor.commit();
            Toast.makeText(c, "Never let me in the dark: "+enable,
                    Toast.LENGTH_LONG).show();
        }else{
            if(sp.contains(ENABLE)){
                return sp.getString(ENABLE, "ERROR");
            }
        }
        return null;
    }
}