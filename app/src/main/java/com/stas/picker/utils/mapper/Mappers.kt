package com.stas.picker.utils.mapper

import com.stas.picker.model.FileType
import com.stas.picker.R
import com.stas.picker.model.MediaItem
import com.stas.picker.model.MediaPath
import com.stas.picker.room.FileItem
import com.stas.picker.utils.extension.checkFileType
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
        MediaItem(this.uri, length = SimpleDateFormat("mm:ss", Locale.getDefault()).format(Date(this.duration)), image = chooseImageDrawable())
    } else {
        MediaItem(this.uri, image = chooseImageDrawable())
    }
}

fun chooseImageDrawable(int: Int = 0): Int {
    return when(int) {
        1 -> R.drawable.ic_1
        2 -> R.drawable.ic_2
        3 -> R.drawable.ic_3
        4 -> R.drawable.ic_4
        5 -> R.drawable.ic_5
        6 -> R.drawable.ic_6
        7 -> R.drawable.ic_7
        8 -> R.drawable.ic_8
        9 -> R.drawable.ic_9
        10 -> R.drawable.ic_10
        11 -> R.drawable.ic_11
        12 -> R.drawable.ic_12
        13 -> R.drawable.ic_13
        14 -> R.drawable.ic_14
        15 -> R.drawable.ic_15
        16 -> R.drawable.ic_16
        17 -> R.drawable.ic_17
        18 -> R.drawable.ic_18
        19 -> R.drawable.ic_19
        20 -> R.drawable.ic_20
        21 -> R.drawable.ic_21
        22 -> R.drawable.ic_22
        23 -> R.drawable.ic_23
        24 -> R.drawable.ic_24
        25 -> R.drawable.ic_25
        26 -> R.drawable.ic_26
        27 -> R.drawable.ic_27
        28 -> R.drawable.ic_28
        29 -> R.drawable.ic_29
        30 -> R.drawable.ic_30
        else -> R.drawable.ic_test
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
    return (DecimalFormat(SIZE_PATTERN).format(this / DEFAULT_SIZE.pow(digitGroups.toDouble()))
            + " " + units[digitGroups])

}

const val DEFAULT_SIZE = 1024.0
const val EMPTY_SIZE = "0"
const val SIZE_PATTERN = "#,##0.#"

