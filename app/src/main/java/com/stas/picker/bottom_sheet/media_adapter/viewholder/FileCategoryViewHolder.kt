package com.stas.picker.bottom_sheet.media_adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.stas.picker.databinding.ItemFileCategoryBinding
import com.stas.picker.model.FileCategory
import java.util.Locale

class FileCategoryViewHolder(
    private val binding: ItemFileCategoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(fileCategoryItem: FileCategory) {
        binding.apply {
            fileNameTextView.text = fileCategoryItem.name.lowercase(Locale.ROOT)
            fileTypeIcon.setImageResource(fileCategoryItem.iconDrawableId)
        }
    }
}