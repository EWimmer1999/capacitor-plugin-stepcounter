package io.capacitor.plugin.stepcounter;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

@CapacitorPlugin(
        name = "StepCounter",
        permissions = {
                @Permission(
                        strings = {Manifest.permission.ACTIVITY_RECOGNITION},
                        alias = StepCounterPlugin.ACTIVITY_RECOGNITION
                )
        }
)
public class StepCounterPlugin extends Plugin implements SensorEventListener {

    static final String ACTIVITY_RECOGNITION = "activityRecognition";

    private SensorManager sensorManager;
    private int stepCount = 0;

    @PluginMethod()
    public void start(PluginCall call) {
        if (getPermissionState(ACTIVITY_RECOGNITION) != PermissionState.GRANTED) {
            requestPermissionForAlias(ACTIVITY_RECOGNITION, call, "pluginPermissionsCallback");
        } else {
            initializeSensor();
            call.resolve();
        }
    }

    private void initializeSensor() {
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if (stepCounterSensor != null) {
                sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
                Log.d("StepCounterPlugin", "Sensor erfolgreich registriert");
            } else {
                Log.d("StepCounterPlugin", "Schrittzähler-Sensor nicht verfügbar");
            }
        } else {
            Log.d("StepCounterPlugin", "SensorManager ist null");
        }
    }
    

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount = (int) event.values[0];
            Log.d("StepCounterPlugin", "Schrittanzahl aktualisiert: " + stepCount);
            JSObject ret = new JSObject();
            ret.put("steps", stepCount);
            notifyListeners("stepCountChange", ret);
        }
    }

    @PermissionCallback
    private void pluginPermissionsCallback(PluginCall call) {
        if (getPermissionState(ACTIVITY_RECOGNITION) == PermissionState.GRANTED) {
            initializeSensor();
            call.resolve();
        } else {
            call.reject("Permission is required to use the step counter");
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for step counter
    }

    @PluginMethod()
    public void getStepCount(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("steps", stepCount); // Verwende den aktuellen Schrittwert
        call.resolve(ret); // Rückgabe des Wertes an den Aufrufer
    }


    @PluginMethod()
    public void stop(PluginCall call) {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            call.resolve();
        } else {
            call.reject("SensorManager is not initialized");
        }
    }

}