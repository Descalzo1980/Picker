package com.stas.picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stas.picker.databinding.RvPickerItemBinding

class RecyclerViewAdapter(
    private val listener: Listener
) : ListAdapter<RecyclerViewAdapter.Types.MediaItem, CustomViewHolder>(MediaItemDiffCallback()) {

    interface Listener {
        fun onClick(mediaItem: Types.MediaItem)
    }

    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = RvPickerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CustomViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == true) {
                holder.bindFavoriteState()
            }
        }
    }

    sealed class Types {
        data class MediaItem(val uri: String?, val chooseIndex: Int, val position: Int)
    }
}