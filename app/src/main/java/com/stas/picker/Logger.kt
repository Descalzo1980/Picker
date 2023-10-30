package com.stas.picker

import android.util.Log

object Logger {

    fun log(text: String) {
        Log.d(TAG, text)
    }

    const val TAG = "MYTAGMYTAG"
}