package com.stas.picker.bottom_sheet.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stas.picker.R
import com.stas.picker.bottom_sheet.adapter.RecyclerViewAdapter
import com.stas.picker.databinding.RvPickerPhotoItemBinding
import com.stas.picker.model.MediaItem
import com.stas.picker.utils.visible

class PhotoViewHolder(
    private val adapter: RecyclerViewAdapter,
    private val binding: RvPickerPhotoItemBinding,
    private val listener: RecyclerViewAdapter.Listener
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: MediaItem

    init {
        binding.ivItemCount.setOnClickListener() {
            listener.onLongClick(item)
        }
    }

    fun bind(item: MediaItem) {
        this.item = item
        binding.apply {
            Glide.with(itemView)
                .load(item.uri)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivMediaItem)
            ivMediaItem.setOnClickListener {
                listener.onClick(item)
            }
            bindFavoriteState(item)
        }
    }

    fun bindFavoriteState(item: MediaItem) {
        binding.apply {
            if (item.choosePosition > 0) {
                tvItemCount.visible(true)
            } else {
                tvItemCount.visible(false)
            }
            tvItemCount.text = item.choosePosition.toString()
        }


    }
}