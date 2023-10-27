package com.stas.picker.bottom_sheet.adapter

import androidx.recyclerview.widget.DiffUtil
import com.stas.picker.model.MediaItem

class MediaItemDiffCallback : DiffUtil.ItemCallback<MediaItem>() {

    override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
        return newItem.uri == oldItem.uri
    }

    override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun getChangePayload(oldItem: MediaItem, newItem: MediaItem): Any? {
        return if (oldItem.choosePosition != newItem.choosePosition) true else null
    }
}