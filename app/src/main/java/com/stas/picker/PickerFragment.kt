package com.stas.picker

import android.content.ContentUris
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stas.picker.bottom_sheet.PickerBottomSheet
import com.stas.picker.databinding.FragmentPickerBinding
import com.stas.picker.model.MediaPath
import com.stas.picker.utils.toDate
import com.stas.picker.utils.toMediaItem

class PickerFragment : Fragment() {

    private var binding: FragmentPickerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPickerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.showBottomSheetButton?.setOnClickListener {
            val dialog = PickerBottomSheet()
            dialog.list = getMediaUris().toMediaItem()
            dialog.show(childFragmentManager, null)
        }

    }

    private fun getMediaUris(): List<MediaPath> {
        val galleryMediaUrls = mutableListOf<MediaPath>()
        val imageColumns = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_MODIFIED)
        val videoColumns = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DATE_MODIFIED, MediaStore.Video.Media.DURATION)

        context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns,
            null, null, null
        )?.use { imageCursor ->
            val idColumn = imageCursor.getColumnIndex(MediaStore.Images.Media._ID)
            val dateColumn = imageCursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)
            while (imageCursor.moveToNext()) {
                val id = imageCursor.getLong(idColumn)
                val date = imageCursor.getLong(dateColumn).toDate()
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id).toString()
                galleryMediaUrls.add(MediaPath(uri, date = date))
            }
        }

        context?.contentResolver?.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns,
            null, null, null
        )?.use { videoCursor ->
            val idColumn = videoCursor.getColumnIndex(MediaStore.Video.Media._ID)
            val dateColumn = videoCursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED)
            val durationColumn = videoCursor.getColumnIndex(MediaStore.Video.Media.DURATION)
            while (videoCursor.moveToNext()) {
                val id = videoCursor.getLong(idColumn)
                val date = videoCursor.getLong(dateColumn).toDate()
                val duration = videoCursor.getLong(durationColumn)
                val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id).toString()
                galleryMediaUrls.add(MediaPath(uri, true, date, duration))
            }
        }
        return galleryMediaUrls.sortedByDescending { it.date }
    }


    companion object {
        const val PHOTO_ITEM = "PHOTO_ITEM"
        const val VIDEO_ITEM = "VIDEO_ITEM"
    }
}