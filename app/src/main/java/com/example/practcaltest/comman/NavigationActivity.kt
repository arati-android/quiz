package com.example.practcaltest.comman

import com.example.practcaltest.QuitAnsListActivity
import com.example.practcaltest.ResultActivity


open class NavigationActivity : BaseActivity() {
    fun openResultActivity() {
        launchActivity<ResultActivity> {
        }
        finish()
    }

    fun openQuesAnsActivity() {
        launchActivity<QuitAnsListActivity> {
        }
        finish()
    }
}