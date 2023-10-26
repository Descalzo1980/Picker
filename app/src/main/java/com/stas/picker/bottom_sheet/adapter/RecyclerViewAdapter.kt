package com.stas.picker.bottom_sheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stas.picker.bottom_sheet.viewholder.PhotoViewHolder
import com.stas.picker.bottom_sheet.viewholder.VideoViewHolder
import com.stas.picker.databinding.RvPickerPhotoItemBinding
import com.stas.picker.databinding.RvPickerVideoItemBinding
import com.stas.picker.model.MediaFile

class RecyclerViewAdapter(
    private val listener: Listener
) : ListAdapter<MediaFile, ViewHolder>(MediaItemDiffCallback()) {

    interface Listener {
        fun onClick(mediaItem: MediaFile)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> {
                holder.bind(currentList[position] as MediaFile.PhotoFile)
            }
            is VideoViewHolder -> {
                holder.bind(currentList[position] as MediaFile.VideoFile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        return when (viewType) {
            FIRST_TYPE -> {
                PhotoViewHolder(
                    RvPickerPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    listener
                )
            }
            SECOND_TYPE -> {
                VideoViewHolder(
                    RvPickerVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    listener
                )
            }
            else -> {throw IllegalArgumentException()}
        }
    }


    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is MediaFile.PhotoFile -> FIRST_TYPE
            is MediaFile.VideoFile -> SECOND_TYPE
        }

    companion object {
        const val FIRST_TYPE = 1
        const val SECOND_TYPE = 2
    }
}