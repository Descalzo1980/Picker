package com.stas.picker.bottom_sheet.adapter

import androidx.recyclerview.widget.DiffUtil
import com.stas.picker.model.MediaFile

class MediaItemDiffCallback : DiffUtil.ItemCallback<MediaFile>() {

    override fun areItemsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
        return when {
            oldItem is MediaFile.PhotoFile && newItem is MediaFile.PhotoFile -> oldItem.uri == newItem.uri
            oldItem is MediaFile.VideoFile && newItem is MediaFile.VideoFile -> oldItem.uri == newItem.uri
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
        return when {
            oldItem is MediaFile.PhotoFile && newItem is MediaFile.PhotoFile -> oldItem.hashCode() == newItem.hashCode()
            oldItem is MediaFile.VideoFile && newItem is MediaFile.VideoFile -> oldItem.hashCode() == newItem.hashCode()
            else -> false
        }
    }
}