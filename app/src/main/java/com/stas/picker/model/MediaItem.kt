package com.stas.picker.model

data class MediaItem(
    val uri: String = EMPTY_STRING,
    var choosePosition: Int = 0,
    val length: String = EMPTY_STRING,
    val isCamera: Boolean = false
)

const val EMPTY_STRING = ""
