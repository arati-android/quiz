package com.example.practcaltest.comman

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "quiz"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences
    private const val totalMark = "totalMark"
    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(
        operation: (SharedPreferences.Editor) -> Unit
    ) {
        val editor = edit()
        operation(editor)

        editor.apply()
    }

    fun clearPref() {
        val editor = preferences.edit()
        editor.apply()
    }

    var QuizTotal: Int
        get() = preferences.getInt(totalMark, -1)
        set(value) = preferences.edit { it.putInt(totalMark, value) }

}