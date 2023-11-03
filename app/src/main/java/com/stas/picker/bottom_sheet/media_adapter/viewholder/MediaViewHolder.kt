package com.stas.picker.bottom_sheet.media_adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stas.picker.R
import com.stas.picker.bottom_sheet.media_adapter.RecyclerViewAdapter
import com.stas.picker.databinding.RvPickerMediaItemBinding
import com.stas.picker.model.EMPTY_STRING
import com.stas.picker.model.MediaItem
import com.stas.picker.utils.extension.invisible

class MediaViewHolder(
    private val binding: RvPickerMediaItemBinding,
    private val listener: RecyclerViewAdapter.Listener
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: MediaItem

    init {
        binding.vChooseClick.setOnClickListener() {
            listener.onPickItemClick(item)
        }
    }

    fun bind(item: MediaItem) {
        this.item = item
        binding.apply {
            Glide.with(itemView.context)
                .load(item.uri)
                .placeholder(R.drawable.ic_media_place_holder)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivMediaItem)
            ivMediaItem.setOnClickListener {
                listener.onMediaClick(item)
            }
            bindChooseState(item)
            bindVideoLength()
        }
    }

    fun bindChooseState(item: MediaItem) {
        binding.apply {
          ivItemCount.setBackgroundResource(item.image)
        }
    }

    private fun bindVideoLength() {
        binding.apply {
            if (item.length != EMPTY_STRING) {
                tvTime.text = item.length
                cvTimeStamp.invisible(true)
            } else {
                cvTimeStamp.invisible(false)
            }
        }
    }
}