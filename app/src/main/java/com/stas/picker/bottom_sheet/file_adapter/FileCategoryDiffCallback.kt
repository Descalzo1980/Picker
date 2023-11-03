package com.stas.picker.bottom_sheet.file_adapter

import androidx.recyclerview.widget.DiffUtil
import com.stas.picker.model.FileType


class FileCategoryDiffCallback : DiffUtil.ItemCallback<FileType>() {

    override fun areItemsTheSame(oldItem: FileType, newItem: FileType): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: FileType, newItem: FileType): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}