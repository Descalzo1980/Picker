package com.stas.picker.model

data class MediaItem(
    val uri: String,
    var choosePosition: Int = 0,
    val length: Long = 0
)
