package com.stas.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        binding?.apply {
            showBottomSheetButton.setOnClickListener {
                showBottomSheet()
            }
        }
    }

    private fun showBottomSheet() {
        val myBottomSheetFragment = MyBottomSheetFragment()
        myBottomSheetFragment.show(requireActivity().supportFragmentManager, MyBottomSheetFragment.TAG)
    }

    companion object {
        const val LENGTH_TYPES = 3
        const val DIVIDER_SPAN = 1
    }
}