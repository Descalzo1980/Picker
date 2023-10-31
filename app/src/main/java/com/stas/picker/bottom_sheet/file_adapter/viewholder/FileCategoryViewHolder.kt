package com.stas.picker.bottom_sheet.file_adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stas.picker.FileType
import com.stas.picker.bottom_sheet.file_adapter.FileCategoryAdapter
import com.stas.picker.databinding.ItemFileCategoryBinding

class FileCategoryViewHolder(
    private val binding: ItemFileCategoryBinding,
    private val fileListener: FileCategoryAdapter.FileListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.fileTypeIcon.setOnClickListener {
            fileListener.onClick(binding.vChecker)
        }
    }
    fun bind(item: FileType) {
        binding.apply {
            Glide.with(fileTypeIcon.context)
                .load(item.uri)
                .placeholder(item.image)
                .error(item.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(fileTypeIcon)

            fileNameTextView.text = item.name
            tvFileSize.text = item.size
        }
    }
}