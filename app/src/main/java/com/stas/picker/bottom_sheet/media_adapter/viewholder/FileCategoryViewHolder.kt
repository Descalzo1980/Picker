package com.stas.picker.bottom_sheet.media_adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.stas.picker.FileCategory
import com.stas.picker.FileCategoryItem
import com.stas.picker.databinding.ItemFileCategoryBinding

class FileCategoryViewHolder(
    private val binding: ItemFileCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(fileCategoryItem: FileCategoryItem) {
        binding.apply {
            fileNameTextView.text = fileCategoryItem.category.name
            fileTypeIcon.setImageResource(fileCategoryItem.category.fileTypes[0].iconResourceId)
        }
    }
}