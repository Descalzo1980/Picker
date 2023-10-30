package com.stas.picker.bottom_sheet.media_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stas.picker.FileCategoryItem
import com.stas.picker.bottom_sheet.media_adapter.viewholder.FileCategoryViewHolder
import com.stas.picker.databinding.ItemFileCategoryBinding

class FileCategoryAdapter : ListAdapter<FileCategoryItem, FileCategoryViewHolder>(FileCategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFileCategoryBinding.inflate(inflater, parent, false)
        return FileCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FileCategoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}