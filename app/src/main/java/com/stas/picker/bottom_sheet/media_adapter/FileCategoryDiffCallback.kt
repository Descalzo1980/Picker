package com.stas.picker.bottom_sheet.media_adapter

import androidx.recyclerview.widget.DiffUtil
import com.stas.picker.FileCategoryItem


class FileCategoryDiffCallback : DiffUtil.ItemCallback<FileCategoryItem>() {
    override fun areItemsTheSame(oldItem: FileCategoryItem, newItem: FileCategoryItem): Boolean {
        return oldItem.category == newItem.category
    }

    override fun areContentsTheSame(oldItem: FileCategoryItem, newItem: FileCategoryItem): Boolean {
        return oldItem == newItem
    }
}