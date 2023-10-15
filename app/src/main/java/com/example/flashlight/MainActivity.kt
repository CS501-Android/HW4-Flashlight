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
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity(), SensorEventListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val action = findViewById<EditText>(R.id.action)
        val switch = findViewById<SwitchCompat>(R.id.flashSwitch)
        val text = findViewById<TextView>(R.id.textView)
        val submit = findViewById<Button>(R.id.submit)

        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val mSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

//        val triggerEventListener = object : TriggerEventListener() {
//            override fun onTrigger(event: TriggerEvent?) {
//                // do Somethign
//            }
//        }
//        mSensor?.also { sensor ->
//            sensorManager.requestTriggerSensor(triggerEventListener, sensor)
//        }


        submit.setOnClickListener {
            if (action.text.toString() == "ON") {
                if (!switch.isChecked) switch.isChecked = true
            } else if (action.text.toString() == "OFF") {
                if (switch.isChecked) switch.isChecked = false
            }
        }

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                text.text = "Good"

                try {
                    cameraManager.setTorchMode(cameraId, true)
                } catch(e: CameraAccessException) {
                    val err = Throwable(e)
                    Log.e("Camera could not turn on! ", err.toString())
                }

            } else {
                text.text = "Bad"
                try {
                    cameraManager.setTorchMode(cameraId, false)
                } catch(e: CameraAccessException) {
                    val err = Throwable(e)
                    Log.e("Camera could not turn off! ", err.toString())
                }
            }
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
       if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
           if (event.values[2] > 9.8) {

           }
       }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}