package com.example.practcaltest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practcaltest.comman.NavigationActivity
import com.example.practcaltest.databinding.ActivitySplashBinding

class SplashActivity : NavigationActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        changeStatusBar(R.color.white, true)
        setContentView(binding.root)

        inti()

    }
    private fun inti() {
        binding.splash.postDelayed({
            openQuesAnsActivity()
        }, 2000)
    }
}