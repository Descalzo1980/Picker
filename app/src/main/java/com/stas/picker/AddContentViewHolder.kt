package com.stas.picker

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.stas.picker.databinding.ItemAddContentBinding

class AddContentViewHolder(
    private val binding: ItemAddContentBinding,
    private val onItemClick: (itemType: AddContentType) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun bind(addContentModel: AddContentModel) {
        binding.apply {
            ivAddItemPreview.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    when (addContentModel.contentType) {
                        AddContentType.GALLERY -> R.drawable.ic_gallery
                        AddContentType.FILE -> R.drawable.ic_file
                        AddContentType.LOCATION -> R.drawable.ic_location
                    }
                )
            )
            tvAddItemTitle.text = itemView.context.getString(
                when (addContentModel.contentType) {
                    AddContentType.GALLERY -> R.string.gallery
                    AddContentType.FILE -> R.string.file
                    AddContentType.LOCATION -> R.string.location
                }
            )
            ivAddItemPreview.alpha = if (addContentModel.isEnabled) 1.0f else 0.5f
            vAddItem.alpha = if (addContentModel.isEnabled) 1.0f else 0.5f
            root.setOnClickListener {
                if (addContentModel.isEnabled) onItemClick(addContentModel.contentType)
            }
        }
    }
}