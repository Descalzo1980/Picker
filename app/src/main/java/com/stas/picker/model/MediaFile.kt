package com.stas.picker.model



sealed class MediaFile {
    data class PhotoFile(val uri: String, var choosePosition: Int = 0) : MediaFile()
    data class VideoFile(val uri: String, val length: Long, var choosePosition: Int = 0) : MediaFile()
}
