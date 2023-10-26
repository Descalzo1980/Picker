package com.stas.picker.bottom_sheet.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stas.picker.R
import com.stas.picker.bottom_sheet.adapter.RecyclerViewAdapter
import com.stas.picker.databinding.RvPickerVideoItemBinding
import com.stas.picker.model.MediaFile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VideoViewHolder(
    private val binding: RvPickerVideoItemBinding,
    private val listener: RecyclerViewAdapter.Listener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MediaFile.VideoFile) {
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
            tvTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(Date(item.length))
        }
    }

    fun bindFavoriteState() {
        binding.ivMediaItem.animate()
    }
}