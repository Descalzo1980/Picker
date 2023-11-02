package com.stas.picker.bottom_sheet.media_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stas.picker.bottom_sheet.media_adapter.viewholder.MediaViewHolder
import com.stas.picker.databinding.RvPickerMediaItemBinding
import com.stas.picker.model.MediaItem

class RecyclerViewAdapter(
    private val listener: Listener
) : ListAdapter<MediaItem, MediaViewHolder>(MediaItemDiffCallback()) {

    interface Listener {
        fun onMediaClick(mediaItem: MediaItem)
        fun onPickItemClick(mediaItem: MediaItem)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(
            RvPickerMediaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.forEach {
                if (it == true) {
                   holder.bindChooseState(currentList[position])
                }
            }
        }
    }
}