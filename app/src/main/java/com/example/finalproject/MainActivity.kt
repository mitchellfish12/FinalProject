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

class MainActivity : AppCompatActivity() {

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

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerView, TextFragment.newInstance("Data"))
            .commit()

        Log.d("Application", "Application Successfully started")

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager //developer.android.com
        locationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) //change to another possibly ambient light
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        findViewById<ImageView>(R.id.ruler).setOnClickListener {
            Log.d("Tab selected", "ruler")
            locationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
            selectedSensor = locationSensor
            sensorManager.registerListener(sensorEventListener, locationSensor, SensorManager.SENSOR_PROXIMITY)
        }
        findViewById<ImageView>(R.id.light).setOnClickListener {
            Log.d("Tab selected", "light")
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            selectedSensor = lightSensor
            sensorManager.registerListener(sensorEventListener, locationSensor, SensorManager.SENSOR_LIGHT)
        }
        findViewById<ImageView>(R.id.weight).setOnClickListener {
            Log.d("Tab selected", "weight")
            gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
            selectedSensor = gravitySensor
            sensorManager.registerListener(sensorEventListener, locationSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                event?.run{
                    if (selectedSensor == locationSensor) {
                        val coordinates = event.values[0]
                        sensorDataTextView.text = "${coordinates.toString()} cm"
                        Log.d("GPS Data:", coordinates.toString())
                    } else if (selectedSensor == lightSensor) {
                        val lightval = event.values[0]
                        Log.d("Sensor Data(Light)", lightval.toString())
                        sensorDataTextView.text = "${lightval.toString()} lights"
                        Log.d("Light sensor level:", lightval.toString())
                    } else if (selectedSensor == gravitySensor) {
                        val x = event.values[0]
                        val y = event.values[1]
                        val z = event.values[2]
                        sensorDataTextView.text = "${y.toString()} lbs"
                        Log.d("Gravity sensor x level:", x.toString())
                        Log.d("Gravity sensor y level:", y.toString())
                        Log.d("Gravity sensor z level:", z.toString())
                        Final(x, y, z)
                    } else {

                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

            }
        }
    }

    private fun Final(x: Float, y: Float, z: Float) {
        if (x == y && x == z) {
            Log.d("Level:", "Success")
        } else {
            Log.d("Level:", "Fail")
        }
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