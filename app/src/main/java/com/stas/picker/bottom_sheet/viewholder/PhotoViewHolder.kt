package com.stas.picker.bottom_sheet.viewholder

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.color.MaterialColors
import com.stas.picker.R
import com.stas.picker.bottom_sheet.adapter.RecyclerViewAdapter
import com.stas.picker.databinding.RvPickerPhotoItemBinding
import com.stas.picker.model.MediaItem
import com.stas.picker.utils.visible

class PhotoViewHolder(
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
                ivItemCount.setCardBackgroundColor(
                    binding.root.context.getColor(R.color.blue)
                )
                ivItemCount.strokeColor = binding.root.context.resources.getColor(R.color.white)
                ivItemCount.alpha = 1f
            } else {
                tvItemCount.visible(false)
                ivItemCount.setCardBackgroundColor(MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorSurface))
                ivItemCount.strokeColor = binding.root.context.resources.getColor(R.color.white)
                ivItemCount.alpha = 0.5f
            }
            tvItemCount.text = item.choosePosition.toString()
        }
    }
}