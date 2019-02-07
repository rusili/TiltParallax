package com.rusili.tiltparallax

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        oneParallax.registerSensorManager()
        sampleParallax.registerSensors()
    }

    override fun onPause() {
        sampleParallax.unregisterSensors()
        oneParallax.unregisterSensorManager()
        super.onPause()
    }
}
