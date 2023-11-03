package com.stas.picker.bottom_sheet.file_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stas.picker.model.FileType
import com.stas.picker.bottom_sheet.file_adapter.viewholder.FileCategoryViewHolder
import com.stas.picker.databinding.ItemFileCategoryBinding

class FileCategoryAdapter(
    private val fileListener: FileListener
) : ListAdapter<FileType, FileCategoryViewHolder>(FileCategoryDiffCallback()) {

    interface FileListener {
        fun onClick(item: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileCategoryViewHolder {
        val binding = ItemFileCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileCategoryViewHolder(binding, fileListener)
    }

    override fun onBindViewHolder(holder: FileCategoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}