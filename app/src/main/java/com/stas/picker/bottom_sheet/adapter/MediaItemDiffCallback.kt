package com.stas.picker.bottom_sheet.adapter

import androidx.recyclerview.widget.DiffUtil

class MediaItemDiffCallback : DiffUtil.ItemCallback<RecyclerViewAdapter.Types.MediaItem>() {
    override fun areItemsTheSame(oldItem: RecyclerViewAdapter.Types.MediaItem, newItem: RecyclerViewAdapter.Types.MediaItem): Boolean {
        return oldItem.position == newItem.position
    }

    override fun areContentsTheSame(oldItem: RecyclerViewAdapter.Types.MediaItem, newItem: RecyclerViewAdapter.Types.MediaItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: RecyclerViewAdapter.Types.MediaItem, newItem: RecyclerViewAdapter.Types.MediaItem): Any? {
        return if (oldItem.position != newItem.position) true else null
    }
}