package com.stas.picker.utils

import com.stas.picker.FileType
import com.stas.picker.model.MediaItem
import com.stas.picker.model.MediaPath
import com.stas.picker.room.FileItem
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow


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
        size = this.size.toSize(),
        res.iconDrawableId,
        name = this.name
    )
}

fun Long.toSize(): String {
    if (this <= 0) {
        return EMPTY_SIZE
    }
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (log10(this.toFloat()) / log10(DEFAULT_SIZE)).toInt()
    return (DecimalFormat("#,##0.#").format(this / DEFAULT_SIZE.pow(digitGroups.toDouble()))
            + " " + units[digitGroups])

}

const val DEFAULT_SIZE = 1024.0
const val EMPTY_SIZE = "0"

