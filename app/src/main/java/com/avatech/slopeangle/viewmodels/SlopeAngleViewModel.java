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

//Subscribes to the phone's sensors to retrieve the phone's angle relative to the
//long edge of the phone.
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

    //Creates a SlopeAngleViewModel.  Called by the view.
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

    //Sets the angle of the phone relative to the long edge of the phone.
    private void setAngle(int value)
    {
        if (angle != value)
        {
            angle = value;
            setDisplayAngle("" + angle);
        }
    }

    //Gets the display angle as a string.
    public String getDisplayAngle()
    {
        return displayAngle;
    }

    //Sets the display angle then notifies the view.
    private void setDisplayAngle(String value)
    {
        if (!displayAngle.equals(value))
        {
            displayAngle = value;
            notifyPropertyChanged(BR.displayAngle);
        }
    }

    //Gets the current radians value.
    public float getRadians()
    {
        return radians;
    }

    //Sets the radians value then notifies the view.
    private void setRadians(float value)
    {
        if (radians != value)
        {
            radians = value;
            notifyPropertyChanged(BR.radians);
        }
    }

    //Enables the sensor listener.
    public void onResume()
    {
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Disables the sensor listener.
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
