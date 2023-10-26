package com.stas.picker.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stas.picker.R
import com.stas.picker.bottom_sheet.adapter.RecyclerItemDecoration
import com.stas.picker.bottom_sheet.adapter.RecyclerViewAdapter
import com.stas.picker.databinding.PickerBottomSheetBinding
import com.stas.picker.model.MediaFile
import com.stas.picker.utils.showAnimation

class PickerBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: PickerBottomSheetBinding
    lateinit var list: MutableList<MediaFile>
    private var itemsList = mutableListOf<MediaFile>()
    private var counter = 0
    private var adapter: RecyclerViewAdapter? = null

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
        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        adapter = RecyclerViewAdapter(object : RecyclerViewAdapter.Listener {
            override fun onClick(mediaItem: MediaFile) {
//                findNavController().navigate(
//                    R.id.action_pickerFragment_to_photoVideoFragment,
//                    bundleOf(
//                        PHOTO_VIDEO_ITEM to mediaItem.uri
//                    )
//                )
            }

            override fun onLongClick(mediaItem: MediaFile) {
                calculatePosition(mediaItem)
            }
        })

        val decorator = RecyclerItemDecoration(SPAN_COUNT, SPACING)
        binding.revMediaPicker.adapter = adapter
        binding.revMediaPicker.layoutManager = layoutManager
        binding.revMediaPicker.addItemDecoration(decorator)
        adapter?.submitList(list)
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

    private fun calculatePosition(mediaFile: MediaFile): Boolean {
        val file = mediaFile as MediaFile.PhotoFile
        return if (file.choosePosition == 0) {
            file.choosePosition = itemsList.size + 1
            itemsList.add(file)
            list[list.indexOf(file)] = file
            adapter?.submitList(list)
//            adapter?.notifyDataSetChanged()
            return true
        } else {
            itemsList.remove(mediaFile)
            itemsList.map { (it as MediaFile.PhotoFile).choosePosition = itemsList.size + 1 }
            false
        }
    }


    companion object {
        private const val SPAN_COUNT = 3
        private const val SPACING = 2
    }
}