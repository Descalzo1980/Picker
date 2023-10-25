package com.stas.picker

import android.content.ContentUris
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager
import androidx.transition.Visibility
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.stas.picker.databinding.FragmentPickerBinding

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
            showSecondDialog()
        }

    }

    private fun showSecondDialog() = binding?.apply{
            val behavior = BottomSheetBehavior.from(bottomSheet.root)
            behavior.peekHeight = (resources.displayMetrics.heightPixels * 0.5).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            behavior.isHideable = true
            test1()
            showBottomNavigation(true)
    }

    private fun test1() {
        val spanCount = 3
        val spacing = 2
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        val adapter = RecyclerViewAdapter(object : RecyclerViewAdapter.Listener {
            override fun onClick(mediaItem: RecyclerViewAdapter.Types.MediaItem) {
                val uri = mediaItem.uri
                    findNavController().navigate(R.id.action_pickerFragment_to_photoVideoFragment, bundleOf(
                        PROTO_VIDEO_ITEM to uri
                    ))
                }
        })
        val itemDecoration = RecyclerItemDecoration(spanCount, spacing)
        binding?.apply {
            bottomSheet.revMediaPicker.adapter = adapter
            bottomSheet.revMediaPicker.layoutManager = layoutManager
            bottomSheet.revMediaPicker.addItemDecoration(itemDecoration)
        }
        adapter.submitList(test().toMediaItem())
    }


    private fun showBottomNavigation(visibility: Boolean) {
        if (isButtonsBarAnimated) return
        binding?.apply {
            val transition = Slide(Gravity.BOTTOM).apply {
                duration = DEFAULT_TRANSITION_DURATION
                addTarget(bvNavigation)
            }.addListener(object : TransitionListenerAdapter() {
                override fun onTransitionStart(transition: Transition) {
                    super.onTransitionStart(transition)
                    isButtonsBarAnimated = true
                }
                override fun onTransitionEnd(transition: Transition) {
                    super.onTransitionEnd(transition)
                    isButtonsBarAnimated = false
                }
            })
            TransitionManager.beginDelayedTransition(bvNavigation, transition)
            bvNavigation.visibility = if (visibility) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }


    private fun test(): List<String> {
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


    private fun List<String?>.toMediaItem(): List<RecyclerViewAdapter.Types.MediaItem> {
        var position = 0
        val result = mutableListOf<RecyclerViewAdapter.Types.MediaItem>()
        result.addAll(
            this.map {
                RecyclerViewAdapter.Types.MediaItem(it, 0, position++)
            }
        )
        return result
    }

    companion object {
        const val LENGTH_TYPES = 3
        const val DIVIDER_SPAN = 1
        const val DEFAULT_TRANSITION_DURATION = 300L
        const val PROTO_VIDEO_ITEM = "PROTO_VIDEO_ITEM"
    }
}