package com.stas.picker.utils

import com.stas.picker.bottom_sheet.adapter.RecyclerViewAdapter

fun List<String?>.toMediaItem(): List<RecyclerViewAdapter.Types.MediaItem> {
    var position = 0
    val result = mutableListOf<RecyclerViewAdapter.Types.MediaItem>()
    result.addAll(
        this.map {
            RecyclerViewAdapter.Types.MediaItem(it, 0, position++)
        }
    )
    return result
}