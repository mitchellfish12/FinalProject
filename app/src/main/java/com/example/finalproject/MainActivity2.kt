package com.example.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.ImageView;

class MainActivity2 : AppCompatActivity() {

    lateinit var sensorManager: SensorManager
    private var selectedSensor: Sensor? = null
    private var locationSensor: Sensor? = null
    private var lightSensor: Sensor? = null
    private var gravitySensor: Sensor? = null
    lateinit var sensorEventListener: SensorEventListener

    private lateinit var sensorDataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager //developer.android.com
        locationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) //change to another possibly ambient light
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        findViewById<ImageView>(R.id.ruler).setOnClickListener {
            locationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
            selectedSensor = locationSensor
            sensorManager.registerListener(sensorEventListener, locationSensor, SensorManager.SENSOR_PROXIMITY)
        }
        findViewById<ImageView>(R.id.light).setOnClickListener {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            selectedSensor = lightSensor
            sensorManager.registerListener(sensorEventListener, locationSensor, SensorManager.SENSOR_LIGHT)
        }
        findViewById<ImageView>(R.id.weight).setOnClickListener {
            gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
            selectedSensor = gravitySensor
            sensorManager.registerListener(sensorEventListener, locationSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                event?.run{
                    if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                        val coordinates = event.values[0]
                        Log.d("Sensor Data(GPS)", coordinates.toString())
                        sensorDataTextView.text = "${coordinates.toString()} cm"
                    } else if (event.sensor.type == Sensor.TYPE_LIGHT) {
                        val lightval = event.values[0]
                        Log.d("Sensor Data(Light)", lightval.toString())
                        sensorDataTextView.text = "${lightval.toString()} lights"
                    } else if (event.sensor.type == Sensor.TYPE_GRAVITY) {
                        val weight = event.values[1]
                        Log.d("Sensor Data(Gravity)", weight.toString())
                        sensorDataTextView.text = "${weight.toString()} lbs"
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

            }
        }
    }

    private fun Display(data: Int) {

    }

    //Registers listener for sensor
    override fun onResume() {
        super.onResume()
        selectedSensor?.let {
            sensorManager?.registerListener(sensorEventListener, selectedSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    //Unregisters listener when activity paused
    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(sensorEventListener)
    }
}