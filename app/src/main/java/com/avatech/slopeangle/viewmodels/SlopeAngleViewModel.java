package com.avatech.slopeangle.viewmodels;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.avatech.slopeangle.BR;

public class SlopeAngleViewModel extends BaseObservable implements SensorEventListener
{
    private SensorManager sensorManager;
    private Resources resources;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;
    private float[] valuesAccelerometer;
    private float[] valuesMagneticField;
    private float[] matrixR;
    private float[] matrixI;
    private float[] matrixValues;

    private int angle;

    @Bindable
    private String displayAngle = "0";

    @Bindable
    private float radians;

    public SlopeAngleViewModel(SensorManager sensorManager, Resources resources)
    {
        this.sensorManager = sensorManager;
        this.resources = resources;

        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        valuesAccelerometer = new float[3];
        valuesMagneticField = new float[3];
        matrixR = new float[9];
        matrixI = new float[9];
        matrixValues = new float[3];
    }

    private void setAngle(int value)
    {
        if (angle != value)
        {
            angle = value;
            setDisplayAngle("" + angle);
        }
    }

    public String getDisplayAngle()
    {
        return displayAngle;
    }

    private void setDisplayAngle(String value)
    {
        if (!displayAngle.equals(value))
        {
            displayAngle = value;
            notifyPropertyChanged(BR.displayAngle);
        }
    }

    public float getRadians()
    {
        return radians;
    }

    private void setRadians(float value)
    {
        if (radians != value)
        {
            radians = value;
            notifyPropertyChanged(BR.radians);
        }
    }

    public void onResume()
    {
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause()
    {
        sensorManager.unregisterListener(this, sensorAccelerometer);
        sensorManager.unregisterListener(this, sensorMagneticField);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        switch(event.sensor.getType())
        {
            case Sensor.TYPE_ACCELEROMETER:
                valuesAccelerometer = event.values.clone();
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                valuesMagneticField = event.values.clone();
                break;
        }

        if (SensorManager.getRotationMatrix(matrixR, matrixI, valuesAccelerometer, valuesMagneticField))
        {
            SensorManager.getOrientation(matrixR, matrixValues);
            setAngle((int) Math.abs(Math.round(Math.toDegrees(matrixValues[1]))));
            double roll = Math.toDegrees(matrixValues[2]);

            float radians = matrixValues[1];
            if (Configuration.ORIENTATION_PORTRAIT == resources.getConfiguration().orientation)
            {
                double degrees = Math.toDegrees(radians);
                if (roll > 0)
                    radians = (float)Math.toRadians(-1 * degrees + 90);
                else
                    radians = (float)Math.toRadians(degrees + 90);
            }
            else
            {
                if (roll > 0)
                {
                    double degrees = Math.toDegrees(radians);
                    radians = (float)Math.toRadians(-1 * degrees);
                }
            }

            setRadians(radians);
        }
    }
}
