package com.stas.picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.stas.picker.databinding.ItemAddContentBinding

class AddContentAdapter(
    private val onItemClick:(itemType: AddContentType) -> Unit
): ListAdapter<AddContentModel, AddContentViewHolder>(
    AsyncDifferConfig.Builder(AddContentDiffCallback()).build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddContentBinding.inflate(inflater, parent, false)
        return AddContentViewHolder(binding){itemType ->
            onItemClick(itemType)
        }
    }

    override fun onBindViewHolder(holder: AddContentViewHolder, position: Int) {
        getItem(position).let { addContentModel ->
            holder.bind(addContentModel)
        }
    }

    fun submitListAdapter(list: List<AddContentModel>) {
        val newAllAddContentItemData = mutableListOf<AddContentModel>()
        newAllAddContentItemData.addAll(list)
        submitList(newAllAddContentItemData)
    }
}