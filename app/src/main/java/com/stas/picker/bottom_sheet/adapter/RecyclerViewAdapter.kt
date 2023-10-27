package com.stas.picker.bottom_sheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stas.picker.bottom_sheet.viewholder.PhotoViewHolder
import com.stas.picker.bottom_sheet.viewholder.VideoViewHolder
import com.stas.picker.databinding.RvPickerPhotoItemBinding
import com.stas.picker.databinding.RvPickerVideoItemBinding
import com.stas.picker.model.MediaItem

class RecyclerViewAdapter(
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        return when (viewType) {
            FIRST_TYPE -> {
                PhotoViewHolder(
                    this,
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
                            holder.bind(currentList[position])
                        }
                    }
                }
            }
        }

    }

//    fun test(mediaFile: MediaFile.PhotoFile) {
//        if (mediaFile.choosePosition == 0) {
//            mediaFile.choosePosition = items.size + 1
//            val list = currentList.toMutableList()
//            val index = list.indexOf(mediaFile)
//            items.add(mediaFile)
//            list.set(index, mediaFile)
//            submitList(list)
//            notifyItemChanged(index)
//        } else {
//            val list = currentList.toMutableList()
//            var count = if (items.size > 1) {
//                1
//            } else {
//                0
//            }
//            mediaFile.choosePosition = 0
//            items.remove(mediaFile)
//            items.map { (it as MediaFile.PhotoFile).choosePosition = 0 }
//            items.map { (it as MediaFile.PhotoFile).choosePosition = count++ }
//            val index = list.indexOf(mediaFile)
//            list.set(index, mediaFile)
//            submitList(list)
//            notifyItemChanged(index)
//            for (item in items) {
//                if (list.contains(item)) {
//                    val index = list.indexOf(item)
//                    list.set(index, item)
//                    submitList(list)
//                    notifyItemChanged(index)
//                }
//            }
//        }
//    }


    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        return if (item.length > 0) {
            SECOND_TYPE
        } else {
            FIRST_TYPE
        }
    }


    companion object {
        const val FIRST_TYPE = 1
        const val SECOND_TYPE = 2
    }
}