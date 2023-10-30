package com.stas.picker.bottom_sheet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stas.picker.R
import com.stas.picker.databinding.FragmentFilePickerBinding


class FilePickerFragment : Fragment() {

    private lateinit var binding: FragmentFilePickerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilePickerBinding.inflate(layoutInflater)
        return binding.root
    }

}