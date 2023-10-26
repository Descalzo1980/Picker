package com.stas.picker.utils

import com.stas.picker.model.MediaFile
import com.stas.picker.model.MediaPath
import java.util.Date


fun List<MediaPath>.toMediaItem(): List<MediaFile> {
    val result = mutableListOf<MediaFile>()
    result.addAll(
        this.map {
            it.toMedia()
        }
    )
    return result
}

fun MediaPath.toMedia(): MediaFile {
    return if (this.isVideo) {
        MediaFile.VideoFile(this.uri, this.duration)
    } else {
        MediaFile.PhotoFile(this.uri)
    }
}

fun Long.toDate(): Date {
    return Date(this)
}