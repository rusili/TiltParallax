package com.rusili.tiltparallax

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
        sampleSingleParallaxView.registerSensor()
        sampleDoubleParallaxView.registerSensors()
    }

    override fun onPause() {
        sampleSingleParallaxView.unregisterSensor()
        sampleDoubleParallaxView.unregisterSensors()
        super.onPause()
    }
}
