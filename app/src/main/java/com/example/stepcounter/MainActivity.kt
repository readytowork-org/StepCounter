package com.example.stepcounter

import android.app.job.JobService
import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener  {
    private var sensorManager:SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        resetSteps()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor : Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if(stepSensor == null){
            Toast.makeText(this , "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        }
        else {
            sensorManager?.registerListener(this , stepSensor , SensorManager.SENSOR_DELAY_UI)
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if(running){
            if (event != null) {
                totalSteps = event.values[0]

               Log.d("step///////////////", "$totalSteps")
                Log.d("step///////////////",  event.timestamp.toString())
              val date =   Date(event.timestamp)
                Log.d("st///////////////", date.toString())

            }
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()

           // tv_stepsTaken.text = ("$currentSteps")
//            progress_circular.apply{
//                setProgressWithAnimation(currentSteps.toFloat())
//            }
        }
    }
    private fun resetSteps(){
//        tv_stepsTaken.setOnClickListener{
//            Toast.makeText(this , "" , Toast.LENGTH_LONG).show()
//        }

//                tv_stepsTaken.setOnClickListener{
//                    previousTotalSteps = totalSteps
//                    tv_stepsTaken.text = 0.toString()
//                    saveData()
//                }
    }

    private fun saveData(){
        val sharedPreferences : SharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor :SharedPreferences.Editor  = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps);
        editor.apply()
    }
  private fun loadData(){
      val sharedPreferences : SharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
      val savedNumber  = sharedPreferences.getFloat("key1" , 0f)
      Log.d("MainActivi", "$savedNumber")
      previousTotalSteps = savedNumber
  }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("on accuracy changed", "$sensor")
    }
}