package com.stas.picker.bottom_sheet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stas.picker.Logger
import com.stas.picker.R
import com.stas.picker.databinding.PickerBottomSheetBinding
import com.stas.picker.utils.showAnimation

class PickerBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: PickerBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PickerBottomSheetBinding.bind(inflater.inflate(R.layout.picker_bottom_sheet, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.log("onViewCreated")
        binding.nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.gallery -> {
                    Logger.log("MediaPickerFragment")
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, MediaPickerFragment.getInstance())
                        .commit()
                    true
                }

                R.id.file -> {
                    true
                }

                R.id.location -> {
                    true
                }

                else -> {
                    Logger.log("FUCK YOU")
                    false}
            }
        }
        binding.nav.selectedItemId = R.id.gallery
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.isFitToContents = true
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        showAnimation(binding.nav, false)
                    }
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED && binding.nav.isVisible.not()) {
                        showAnimation(binding.nav, true)
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset >= 0) {
                        binding.nav.y = ((bottomSheet.parent as View).height - bottomSheet.top - binding.nav.height).toFloat()
                    }
                }
            }.apply {
                binding.root.post {onSlide(binding.root.parent as View, 0f)}
            })
        }
    }


    companion object {
        private const val SPAN_COUNT = 3
        private const val SPACING = 2
    }
}