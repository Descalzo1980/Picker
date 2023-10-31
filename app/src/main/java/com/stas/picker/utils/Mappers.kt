package com.stas.picker.utils

import com.stas.picker.FileType
import com.stas.picker.model.MediaItem
import com.stas.picker.model.MediaPath
import com.stas.picker.room.FileItem
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

fun List<FileItem>.toFileType(): MutableList<FileType> {
    val result = mutableListOf<FileType>()
    result.addAll(
        this.map {
            it.toFileType()
        }
    )
    return result
}

fun FileItem.toFileType(): FileType {
    val res = checkFileType(this.extension)
    return FileType(
        uri = this.uri,
        extension = this.extension,
        size = this.size.toString(),
        res.iconDrawableId,
        name = this.name
    )
}

