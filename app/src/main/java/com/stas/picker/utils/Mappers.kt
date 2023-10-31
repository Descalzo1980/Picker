package com.stas.picker.utils

import com.stas.picker.model.MediaItem
import com.stas.picker.model.MediaPath
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun List<MediaPath>.toMediaItem(): MutableList<MediaItem> {
    val result = mutableListOf<MediaItem>()
    result.addAll(
        this.map {
            it.toMedia()
        }
    )
    return result
}

fun MediaPath.toMedia(): MediaItem {
    return if (this.isVideo) {
        MediaItem(this.uri, length = SimpleDateFormat("mm:ss", Locale.getDefault()).format(Date(this.duration)))
    } else {
        MediaItem(this.uri)
    }
}

fun Long.toDate(): Date {
    return Date(this)
}

