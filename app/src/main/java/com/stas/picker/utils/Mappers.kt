package com.stas.picker.utils

import com.stas.picker.model.MediaItem
import com.stas.picker.model.MediaPath
import java.util.Date


fun List<MediaPath>.toMediaItem(): MutableList<MediaItem> {
    val result = mutableListOf<MediaItem>()
    result.add(MediaItem(isCamera = true))
    result.addAll(
        this.map {
            it.toMedia()
        }
    )
    return result
}

fun MediaPath.toMedia(): MediaItem {
    return if (this.isVideo) {
        MediaItem(this.uri, length = this.duration)
    } else {
        MediaItem(this.uri)
    }
}

fun Long.toDate(): Date {
    return Date(this)
}