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
import com.stas.picker.utils.toMediaItem

class PickerFragment : Fragment() {

    private var binding: FragmentPickerBinding? = null
    private var isButtonsBarAnimated = false

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

    private fun getMediaUris(): List<String> {
        val galleryMediaUrls = mutableListOf<String>()
        val imageColumns = arrayOf(MediaStore.Images.Media._ID)
        val videoColumns = arrayOf(MediaStore.Video.Media._ID)
        val imageOrderBy = MediaStore.Images.Media.DATE_TAKEN
        val videoOrderBy = MediaStore.Video.Media.DATE_TAKEN

        context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns,
            null, null, "$imageOrderBy DESC"
        )?.use { imageCursor ->
            val idColumn = imageCursor.getColumnIndex(MediaStore.Images.Media._ID)

            while (imageCursor.moveToNext()) {
                val id = imageCursor.getLong(idColumn)

                galleryMediaUrls.add(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id).toString())
            }
        }
        context?.contentResolver?.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns,
            null, null, "$videoOrderBy DESC"
        )?.use { videoCursor ->
            val idColumn = videoCursor.getColumnIndex(MediaStore.Video.Media._ID)
            while (videoCursor.moveToNext()) {
                val id = videoCursor.getLong(idColumn)
                galleryMediaUrls.add(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id).toString())
            }
        }
        return galleryMediaUrls
    }

    companion object {
        const val PHOTO_VIDEO_ITEM = "PROTO_VIDEO_ITEM"
    }
}