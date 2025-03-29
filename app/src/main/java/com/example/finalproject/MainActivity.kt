package com.example.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private var locationSensor: Sensor? = null
    private var tempSensor: Sensor? = null
    private var soundSensor: Sensor? = null

    private lateinit var locationTextView: TextView
    private lateinit var tempTextView: TextView
    private lateinit var soundTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager //developer.android.com
        locationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        //soundSensor = sensorManager.getDefaultSensor(Sensor.TYPE_)

        tempTextView = findViewById(R.id.tempTextView)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.reisterListener(this, it, SensorManager.SENSOR_PROXIMITY)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {

        } else if (even.sensor.type == Sensor.TYPE_PROXIMITY) {

        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}


}