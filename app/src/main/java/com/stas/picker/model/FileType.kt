package com.stas.picker.model

import androidx.annotation.DrawableRes

data class FileType(
    val uri: String,
    val extension: String = EMPTY_STRING,
    val size: String = EMPTY_STRING,
    @DrawableRes
    val image: Int,
    val name: String = EMPTY_STRING
)
