package com.example.practcaltest.comman

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate


class Controller : Application() {

    var context: BaseActivity? = null
    var role = "user"
    override fun onCreate() {
        super.onCreate()
        instance = this
        contextInstance = this
        AppPreferences.init(this)

        setScreenRotation()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)



    }

    private fun setScreenRotation() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            @SuppressLint("SourceLockedOrientationActivity")
            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(p0: Activity) {
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }

        })


    }




    companion object {
        lateinit var instance: Controller
        var contextInstance: Controller? = null
        var listener: ((String, String, Int, String) -> Unit?)? = null
    }

}

