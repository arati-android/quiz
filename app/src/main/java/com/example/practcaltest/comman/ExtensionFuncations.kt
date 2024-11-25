package com.example.practcaltest.comman

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.practcaltest.BuildConfig
import com.google.android.material.slider.RangeSlider
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
 */
operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}


private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}


inline operator fun <reified T : Any> SharedPreferences.get(
    key: String, defaultValue: T? = null
): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}





inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (requestCode == -1)
        startActivity(intent)
    else
        startActivityForResult(intent, requestCode)
}



inline fun <reified T : Any> Fragment.launchActivity(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this.requireContext())
    intent.init()
    if (requestCode == -1)
        startActivity(intent)
    else
        startActivityForResult(intent, requestCode)

}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)


















