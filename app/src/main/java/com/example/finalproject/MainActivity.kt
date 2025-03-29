package com.example.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private var GPS: Sensor? = null
    private var Temp: Sensor? = null
    private var Microphone: Sensor? = null
    private lateinit var text: TextView



}