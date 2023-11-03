package com.stas.picker.utils.logger

import android.util.Log

object Logger {

    fun log(text: String) {
        Log.d(TAG, text)
    }

    private const val TAG = "MYTAGMYTAG"
}