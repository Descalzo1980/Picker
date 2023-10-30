package com.stas.picker.utils

import android.webkit.MimeTypeMap
import com.stas.picker.FileCategory
import com.stas.picker.FileCategoryItem
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

fun FileItem.toFileCategory(): FileCategoryItem {
    val fileType = FileCategory.getFileTypeByExtension(this.extension)
    val category = fileType?.let { fileCategoryForFileType(it) } ?: FileCategory.UNKNOWN
    return FileCategoryItem(category)
}

fun fileCategoryForFileType(fileType: FileType): FileCategory {
    return FileCategory.values().find { fileType in it.fileTypes } ?: FileCategory.UNKNOWN
}