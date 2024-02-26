package com.example.coursesapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.Timer
import java.util.TimerTask

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Timer().schedule(object: TimerTask(){
            override fun run() {
                val intent= Intent(this@SplashScreenActivity,RegisterActivity::class.java)
                startActivity(intent)

            }
        },4000)

    }
}