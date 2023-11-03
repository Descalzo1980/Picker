package com.stas.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stas.picker.bottom_sheet.ui.PickerBottomSheetFragment
import com.stas.picker.databinding.FragmentPickerBinding

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
            val dialog = PickerBottomSheetFragment()
            dialog.show(childFragmentManager, null)
        }
    }


    companion object {
        const val PHOTO_ITEM = "PHOTO_ITEM"
        const val VIDEO_ITEM = "VIDEO_ITEM"
    }
}