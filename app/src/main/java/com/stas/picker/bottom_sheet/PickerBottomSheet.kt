package com.stas.picker.bottom_sheet

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stas.picker.PickerFragment.Companion.PHOTO_VIDEO_ITEM
import com.stas.picker.R
import com.stas.picker.bottom_sheet.adapter.RecyclerItemDecoration
import com.stas.picker.bottom_sheet.adapter.RecyclerViewAdapter
import com.stas.picker.databinding.BottomSheetNavigationBinding
import com.stas.picker.databinding.PickerBottomSheetBinding

class PickerBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: PickerBottomSheetBinding
    lateinit var list: List<RecyclerViewAdapter.Types.MediaItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PickerBottomSheetBinding.bind(inflater.inflate(R.layout.picker_bottom_sheet, container))
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val containerLayout = dialog?.findViewById(
                com.google.android.material.R.id.container
            ) as? FrameLayout

            val menuBinding = BottomSheetNavigationBinding.inflate(
                LayoutInflater.from(requireContext())
            )

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )

            containerLayout?.addView(
                menuBinding.root,
                layoutParams
            )

            menuBinding.root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    menuBinding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val height = menuBinding.root.measuredHeight
                    binding.root.setPadding(0, 0, 0, height ?: 0)
                }
            })
        }
        return bottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        val adapter = RecyclerViewAdapter(object : RecyclerViewAdapter.Listener {
            override fun onClick(mediaItem: RecyclerViewAdapter.Types.MediaItem) {
                findNavController().navigate(
                    R.id.action_pickerFragment_to_photoVideoFragment,
                    bundleOf(
                        PHOTO_VIDEO_ITEM to mediaItem.uri
                    )
                )
            }
        })
        val decorator = RecyclerItemDecoration(SPAN_COUNT, SPACING)
        binding.revMediaPicker.adapter = adapter
        binding.revMediaPicker.layoutManager = layoutManager
        binding.revMediaPicker.addItemDecoration(decorator)
        adapter.submitList(list)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.isFitToContents = true
        }
    }


    companion object {
        private const val SPAN_COUNT = 3
        private const val SPACING = 2
    }
}