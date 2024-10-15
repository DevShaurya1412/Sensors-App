package com.example.sensorsapp

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensor:  Sensor?= null
    var sensorManager: SensorManager?= null
    lateinit var compassImage: ImageView
    lateinit var degreeTV: TextView

    //to keep track of rotation of the compass
    var currentDegree = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        compassImage = findViewById(R.id.imageView)
        degreeTV = findViewById(R.id.degreeTV)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        //most important function
        var degree= Math.round(p0!!.values[0])
        var rotationAnimation = RotateAnimation(currentDegree,(-degree).toFloat(),Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        Log.d(TAG,"on Sensor changed:" +degree)
        degreeTV.setText(degree).toString() + "degrees"
        rotationAnimation.duration = 210
        rotationAnimation.fillAfter= true
        compassImage.startAnimation(rotationAnimation)
        currentDegree = (-degree).toFloat()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    //Registering a listener
    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME)

    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}