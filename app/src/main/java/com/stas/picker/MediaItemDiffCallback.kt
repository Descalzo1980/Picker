package com.stas.picker

import androidx.recyclerview.widget.DiffUtil

class MediaItemDiffCallback : DiffUtil.ItemCallback<RecyclerViewAdapter.Types.MediaItem>() {
    override fun areItemsTheSame(oldItem: RecyclerViewAdapter.Types.MediaItem, newItem: RecyclerViewAdapter.Types.MediaItem): Boolean {
        return oldItem.position == newItem.position
    }

    override fun areContentsTheSame(oldItem: RecyclerViewAdapter.Types.MediaItem, newItem: RecyclerViewAdapter.Types.MediaItem): Boolean {
        return oldItem == newItem
    }
}