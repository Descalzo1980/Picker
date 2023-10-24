package com.stas.picker

import android.content.ContentUris
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager
import androidx.transition.Visibility
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
        showBottomSheetButton.setOnClickListener {
            val behavior = BottomSheetBehavior.from(bottomSheet.root)
            behavior.peekHeight = (resources.displayMetrics.heightPixels * 0.5).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            behavior.isHideable = true
            test1()
            showBottomNavigation(true)
        }
    }

    private fun test1() {
        val spanCount = 3
        val spacing = 2
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        val adapter = RecyclerViewAdapter(object : RecyclerViewAdapter.Listener {
            override fun onClick(chooseIndex: Int, callback: (Int) -> Unit) {
                if (calculateIndex(chooseIndex)) {
                    callback.invoke(chooseIndex)
                } else {
                    Unit
                }
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

    private fun calculateIndex(chooseIndex: Int): Boolean {
        return chooseIndex == 0
    }

    private fun test(): List<String> {
        val galleryImageUrls = mutableListOf<String>()
        val columns = arrayOf(MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN

        context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
            null, null, "$orderBy DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)

                galleryImageUrls.add(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id).toString())
            }
        }
        return galleryImageUrls
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
    }
}