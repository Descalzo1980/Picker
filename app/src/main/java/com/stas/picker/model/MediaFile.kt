package com.stas.picker.model



sealed class MediaFile {
    data class PhotoFile(val uri: String, val choosePosition: Int = 0) : MediaFile()
    data class VideoFile(val uri: String, val length: Long, val choosePosition: Int = 0) : MediaFile()
}
