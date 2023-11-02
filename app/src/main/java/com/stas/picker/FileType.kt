package com.stas.picker

import android.net.Uri
import androidx.annotation.DrawableRes
import com.stas.picker.model.EMPTY_STRING

data class FileType(
    val uri: String,
    val extension: String = EMPTY_STRING,
    val size: String = EMPTY_STRING,
    @DrawableRes
    val image: Int,
    val name: String = EMPTY_STRING
)
