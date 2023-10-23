package com.stas.picker

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stas.picker.databinding.RvPickerItemBinding

class CustomViewHolder(
    private val binding: RvPickerItemBinding,
    private val listener: RecyclerViewAdapter.Listener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RecyclerViewAdapter.Types.MediaItem) {
        binding.apply {
            Glide.with(itemView)
                .load(item.uri)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivMediaItem)
            ivMediaItem.setOnClickListener {
                listener.onClick(item.chooseIndex) { callbackPosition ->

                }
            }
            ivItemCount.setOnClickListener {
                listener.onClick(item.chooseIndex) { callbackPosition ->

                }
            }
        }
    }
}