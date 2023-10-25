package com.stas.picker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stas.picker.databinding.FragmentPhotoVideoBinding
import com.stas.picker.databinding.FragmentPickerBinding

class PhotoVideoFragment : Fragment() {

    private var binding: FragmentPhotoVideoBinding? = null

    private val photoVideoFragment: Boolean by lazy { arguments?.getString(PickerFragment.PROTO_VIDEO_ITEM) != null }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoVideoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo = binding?.ivMediaItem
        if (photoVideoFragment) {
            val photoPath = arguments?.getString(PickerFragment.PROTO_VIDEO_ITEM)
            if (!photoPath.isNullOrEmpty()) {
                Glide.with(this)
                    .load(photoPath)
                    .into(photo!!)
            }
        }
    }
}