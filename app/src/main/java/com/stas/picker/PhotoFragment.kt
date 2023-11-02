package com.stas.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.stas.picker.databinding.FragmentPhotoBinding
import com.stas.picker.utils.anim.TransitionsHandler

class PhotoFragment : DialogFragment() {

    private var binding: FragmentPhotoBinding? = null

    private val photoVideoFragment: Boolean by lazy { arguments?.getString(PickerFragment.PHOTO_ITEM) != null }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo = binding?.ivMediaItem
        if (photoVideoFragment) {
            val photoPath = arguments?.getString(PickerFragment.PHOTO_ITEM)
            if (!photoPath.isNullOrEmpty()) {
                if (photo != null) {
                    Glide.with(this)
                        .load(photoPath)
                        .into(photo)
                }
            }
        }
    }

//    override fun getTheme(): Int {
//        return R.style.DialogTheme
//    }
}