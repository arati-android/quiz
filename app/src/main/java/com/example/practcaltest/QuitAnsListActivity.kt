package com.example.practcaltest

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.practcaltest.adapter.AdapterQues
import com.example.practcaltest.comman.AppPreferences
import com.example.practcaltest.comman.NavigationActivity
import com.example.practcaltest.databinding.ActivityQuitAnsListBinding
import com.example.practcaltest.viewmodel.ListViewModel

class QuitAnsListActivity : NavigationActivity() {
    private lateinit var binding: ActivityQuitAnsListBinding
    private var adapterQues: AdapterQues? = null
    private val listViewModel: ListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuitAnsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeStatusBar(R.color.white, true)

        inti()
    }

    private fun inti() {
        setUpToolBar(binding.imgBack, binding.tvHeading, getString(R.string.quiz))
        setAdapter()
    }

    private fun setAdapter() {
        binding.progress.isVisible=true
        adapterQues = AdapterQues{right,wrong->
            binding.textRight.text=right
            binding.textWrong.text=wrong

            AppPreferences.QuizTotal=right.toInt()
        }
        binding.rv.adapter = adapterQues
       // binding.rv.
        binding.main.isVisible=false

        callListApi()
    }

    private fun callListApi() {
        val param = HashMap<String, Any>()
        param["amount"] = 10
        param["category"] = 9
        param["difficulty"] = "easy"
        param["type"] = "boolean"

        listViewModel.callListApi(param){
            binding.progress.isVisible=false
            binding.main.isVisible=true
            adapterQues?.differ?.submitList(it)
            binding.rv.setItemViewCacheSize(it.size)
        }
    }
}