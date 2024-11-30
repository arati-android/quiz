package com.example.practcaltest.comman

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Base64
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.practcaltest.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.io.File
import kotlin.coroutines.CoroutineContext


open class BaseActivity : AppCompatActivity(), CoroutineScope {
    val TAG: String = this::class.java.simpleName

    protected var page = 1
    protected var totalPage = 1
    protected var getIsLoading = false
    protected var getIsLastPage = false
    var callApi = true
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val job = Job()
    lateinit var takePicture: ActivityResultLauncher<Uri>
    var finalPickerCallBack: ((Uri, String) -> Unit?)? = null
    var pickerCallBack: ((String) -> Unit?)? = null

    val which = ""
    var settingCallBack: (() -> Unit)? = null
    fun makeToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        changeStatusBar(R.color.white, true)

    }

    var dialog: AppCompatDialog? = null
    fun showProgress() {
        try {
            dialog = AppCompatDialog(this, R.style.dialogTheme)
            dialog?.setCancelable(false)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //dialog?.setContentView(R.layout.progress_loading)
            dialog?.show()
        } catch (_: Exception) {
        }
    }

    internal fun hideProgress() {
        try {
            dialog?.dismiss()
            dialog = null
        } catch (_: Exception) {
        }
    }

    fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    fun File.toBase64(): String? {
        val result: String?
        inputStream().use { inputStream ->
            val sourceBytes = inputStream.readBytes()
            result = Base64.encodeToString(sourceBytes, Base64.DEFAULT)
        }

        return result
    }
    fun changeStatusBar(colorPrimary: Int, lightStatusbar: Boolean = false) {
        window.statusBarColor = ContextCompat.getColor(this, colorPrimary)

        val window: Window = window
        val decorView: View = window.decorView

        if (lightStatusbar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val wic = decorView.windowInsetsController
                wic!!.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                val wic = WindowInsetsControllerCompat(window, decorView)
                wic.isAppearanceLightStatusBars = true
//                window.statusBarColor = colorPrimary
            }
        } else {
            val wic = WindowInsetsControllerCompat(window, decorView)
            wic.isAppearanceLightStatusBars = false
        }
    }


    protected fun setUpToolBar(
        ivBack: ShapeableImageView,
        tvHeading: MaterialTextView,
        string: String,

        ) {
        try {
            tvHeading.text=string
            ivBack.setOnClickListener {
                hideKeyBoard()
                onBackPressedDispatcher.onBackPressed()
            }
        } catch (e: Exception) {

        }
    }





}