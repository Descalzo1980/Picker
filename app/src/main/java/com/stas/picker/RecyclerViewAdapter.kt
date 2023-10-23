package com.stas.picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stas.picker.databinding.RvPickerItemBinding

class RecyclerViewAdapter(
    private val listener: Listener
) : ListAdapter<RecyclerViewAdapter.Types.MediaItem, CustomViewHolder>(MediaItemDiffCallback()) {

    interface Listener {
        fun onClick(chooseIndex: Int, callback: (Int) -> Unit)
    }

    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = RvPickerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    sealed class Types {
        data class MediaItem(val uri: String?, val chooseIndex: Int, val position: Int)
    }
}