package com.example.practcaltest

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practcaltest.comman.AppPreferences
import com.example.practcaltest.comman.NavigationActivity
import com.example.practcaltest.databinding.ActivityQuitAnsListBinding
import com.example.practcaltest.databinding.ActivityResultBinding

class ResultActivity : NavigationActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeStatusBar(R.color.white, true)
        inti()
    }

    private fun inti() {
        binding.tvScore.text=String.format(AppPreferences.QuizTotal.toString() + "/" + "10")
    }
}