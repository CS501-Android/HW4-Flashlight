package com.example.flashlight

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var cameraManager: CameraManager
    private lateinit var gestureDetector: GestureDetector
    private lateinit var switch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val action = findViewById<EditText>(R.id.action)
        switch = findViewById(R.id.flashSwitch)
        val submit = findViewById<Button>(R.id.submit)

        gestureDetector = GestureDetector(this)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        submit.setOnClickListener {
            if (action.text.toString() == "ON") {
                if (!switch.isChecked) switch.isChecked = true
            } else if (action.text.toString() == "OFF") {
                if (switch.isChecked) switch.isChecked = false
            }
        }

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                turnCamOn(cameraManager)

            } else {
               turnCamOff(cameraManager)
            }
        }

    }


    private fun turnCamOn(cam: CameraManager) {
        try {
            cam.setTorchMode(cam.cameraIdList[0], true)
        } catch(e: CameraAccessException) {
            val err = Throwable(e)
            Log.e("Camera could not turn on!", err.toString())
        }
    }

    private fun turnCamOff(cam: CameraManager) {
        try {
            cam.setTorchMode(cam.cameraIdList[0], false)
        } catch(e: CameraAccessException) {
            val err = Throwable(e)
            Log.e("Camera could not turn off!", err.toString())
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        }
        else {
            super.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent) {
        return
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {
        return
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1 != null && e1.y > e2.y) {
            //turnCamOn(cameraManager)
            switch.isChecked = true
        } else if (e1 != null && e1.y < e2.y) {
            //turnCamOff(cameraManager)
            switch.isChecked = false
        }

        return true
    }
}
