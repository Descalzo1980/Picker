package com.stas.picker.model

import androidx.annotation.DrawableRes

data class MediaItem(
    val uri: String = EMPTY_STRING,
    var choosePosition: Int = 0,
    val length: String = EMPTY_STRING,
    val isCamera: Boolean = false,
    @DrawableRes var image: Int = 0
)

const val EMPTY_STRING = ""
