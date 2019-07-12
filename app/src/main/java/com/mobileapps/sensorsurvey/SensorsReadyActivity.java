package com.mobileapps.sensorsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.widget.TextView;

public class SensorsReadyActivity extends AppCompatActivity implements SensorEventListener
{
    // Individual light and proximity sensors.
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    // TextViews to display current sensor values
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;

    private SensorManager mSensorManager;

    private ImageView imgLightLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_ready);
        mTextSensorLight = (TextView) findViewById(R.id.label_light);
        mTextSensorProximity = (TextView) findViewById(R.id.label_proximity);
        mSensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorProximity =
                mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        String sensor_error = getResources().getString(R.string.error_no_sensor);

        imgLightLevel = findViewById(R.id.imgLightLevel);

        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }

        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }


    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT:
                // Handle light sensor
                mTextSensorLight.setText(getResources().getString(
                        R.string.label_light, currentValue));

                if (currentValue == 0)
                imgLightLevel.setBackgroundColor(getResources().getColor(R.color.colorNoLight));
                else
                if(currentValue <0.4)
                    imgLightLevel.setBackgroundColor(getResources().getColor(R.color.colorSomeLight));
                else
                    if (currentValue < 1.2)
                        imgLightLevel.setBackgroundColor(getResources().getColor(R.color.colorNormalLight));
else if (currentValue <3 )
                        imgLightLevel.setBackgroundColor(getResources().getColor(R.color.colorMoreThanNormalLight));
else if (currentValue>=3)
                        imgLightLevel.setBackgroundColor(getResources().getColor(R.color.colorTooMuchLight));



                break;
            case Sensor.TYPE_PROXIMITY:
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imgLightLevel.getLayoutParams();
                mTextSensorProximity.setText(getResources().getString(
                        R.string.label_proximity, currentValue));
                        if(currentValue < 1)
                        {
                            params.width = 50;
                            params.height = 50;
                        }
                        else
                        if(currentValue < 2)
                        {
                            params.width = 80;
                            params.height = 80;
                        }
                        else
                        if(currentValue < 3)
                        {
                            params.width = 100;
                            params.height = 100;
                        }
                        else
                        if(currentValue < 5)
                        {
                            params.width = 150;
                            params.height = 150;
                        }
                        else
                        if(currentValue >= 5)
                        {
                            params.width = 250;
                            params.height = 250;
                        }


                break;
            default:
                // do nothing
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
