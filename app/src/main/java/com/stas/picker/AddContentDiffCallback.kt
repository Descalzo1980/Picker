package com.stas.picker

import androidx.recyclerview.widget.DiffUtil

class AddContentDiffCallback : DiffUtil.ItemCallback<AddContentModel>() {
    override fun areItemsTheSame(oldItem: AddContentModel, newItem: AddContentModel): Boolean {
        return oldItem.contentType == newItem.contentType
    }

    override fun areContentsTheSame(oldItem: AddContentModel, newItem: AddContentModel): Boolean {
        return oldItem == newItem
    }
}