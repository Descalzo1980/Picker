package com.stas.picker.bottom_sheet.media_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stas.picker.bottom_sheet.viewholder.CameraViewHolder
import com.stas.picker.bottom_sheet.viewholder.PhotoViewHolder
import com.stas.picker.bottom_sheet.viewholder.VideoViewHolder
import com.stas.picker.databinding.RvCameraPreviewItemBinding
import com.stas.picker.databinding.RvPickerPhotoItemBinding
import com.stas.picker.databinding.RvPickerVideoItemBinding
import com.stas.picker.model.MediaItem

class RecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val listener: Listener
) : ListAdapter<MediaItem, ViewHolder>(MediaItemDiffCallback()) {

    interface Listener {
        fun onClick(mediaItem: MediaItem)
        fun onLongClick(mediaItem: MediaItem)
    }

    private val items = mutableListOf<MediaItem>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> {
                holder.bind(currentList[position])
            }
            is VideoViewHolder -> {
                holder.bind(currentList[position])
            }
            is CameraViewHolder -> {
                holder.bind()
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
            THIRD_TYPE -> {
                CameraViewHolder(
                    lifecycleOwner,
                    RvCameraPreviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {throw IllegalArgumentException()}
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.forEach {
                if (it == true) {
                    when (holder) {
                        is PhotoViewHolder -> {
                            holder.bindFavoriteState(currentList[position])
                        }
                        is VideoViewHolder -> {
                            holder.bindFavoriteState(currentList[position])
                        }
                    }
                }
            }
        }

    }


    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        return if (item.isCamera) {
            THIRD_TYPE
        } else if (item.length > 0) {
            SECOND_TYPE
        } else {
            FIRST_TYPE
        }
    }


    companion object {
        const val FIRST_TYPE = 1
        const val SECOND_TYPE = 2
        const val THIRD_TYPE = 3
    }
}