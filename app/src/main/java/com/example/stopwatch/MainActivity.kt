package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var isRunning = false
    private var startTime = 0L
    private var elapsedTime = 0L
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

            binding.startbutton.setOnClickListener{
                startStopwatch()
            }

        binding.pausebutton.setOnClickListener{
            pauseStopwatch()
        }
        binding.restartbutton.setOnClickListener{
            resetStopwatch()
}
    }

    private fun startStopwatch() {
        if (!isRunning) {
            startTime = SystemClock.uptimeMillis() - elapsedTime
            handler.post(updateTimerRunnable)
            isRunning = true
        }
    }

    private fun pauseStopwatch() {
        if (isRunning) {
            elapsedTime = SystemClock.uptimeMillis() - startTime
            handler.removeCallbacks(updateTimerRunnable)
            isRunning = false
        }
    }

    private fun resetStopwatch() {
        elapsedTime = 0L
        startTime = 0L
        binding.timer.text = "00:00:000"
        handler.removeCallbacks(updateTimerRunnable)
        isRunning = false
    }

    private val updateTimerRunnable = object : Runnable {
        override fun run() {
            val currentTime = SystemClock.uptimeMillis() - startTime
            val minutes = (currentTime / 60000).toInt()
            val seconds = ((currentTime % 60000) / 1000).toInt()
            val milliseconds = (currentTime % 1000).toInt()

            binding.timer.text = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
            handler.postDelayed(this, 10) // Update every 10 milliseconds
        }
    }
}
