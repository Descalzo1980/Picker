package com.stas.picker.bottom_sheet.ui

import android.content.ContentUris
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stas.picker.utils.logger.Logger
import com.stas.picker.R
import com.stas.picker.bottom_sheet.view_model.PickerViewModel
import com.stas.picker.databinding.PickerBottomSheetBinding
import com.stas.picker.model.MediaPath
import com.stas.picker.utils.anim.showAnimation
import com.stas.picker.utils.mapper.toDate
import com.stas.picker.utils.mapper.toMediaItem


class PickerBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: PickerBottomSheetBinding
    private lateinit var viewModel: PickerViewModel
    private var alertDialog: MaterialAlertDialogBuilder? = null
    private var defaultRefreshRate: Float = 0f

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
        getDefaultRefreshRate()
//        updatePeekHeight(originalHeight, updatedHeight)
        dialog?.window?.attributes?.windowAnimations = R.style.BottomSheetDialogAnimation;
        setFreshRate(BOTTOM_SHEET_RATE)
        viewModel = ViewModelProvider(requireActivity())[PickerViewModel::class.java]
        if (viewModel.listItems.value.isEmpty()) {
            viewModel.setList(getMediaUris().toMediaItem())
        }
        Logger.log("onViewCreated")
        setUpOutsideTouchListener()
        binding.nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.gallery -> {
                    Logger.log("MediaPickerFragment")
                    childFragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .setCustomAnimations(R.anim.to_in, R.anim.to_out)
                        .replace(R.id.container, MediaPickerFragment.getInstance())
                        .commit()
                    true
                }

                R.id.file -> {
                    childFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.to_in, R.anim.to_out)
                        .replace(R.id.container, FilePickerFragment())
                        .commit()
                    true
                }

                R.id.location -> {
                    Logger.log("Not implemented yet")
                    true
                }

                else -> {
                    Logger.log("Not implemented yet")
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
            behavior.peekHeight = (requireActivity().resources.displayMetrics.heightPixels * 0.5).toInt()
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        showAnimation(binding.nav, false)
//                        binding.appBarLayout.invisible(true)
//                        binding.llDragHandle.invisible(false)
                    }
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED && binding.nav.isVisible.not()) {
                        showAnimation(binding.nav, true)
//                        binding.appBarLayout.invisible(false)
//                        binding.llDragHandle.invisible(true)
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    Logger.log("slideOffset = $slideOffset")
                    if (slideOffset >= 0) {
                        binding.nav.y = ((bottomSheet.parent as View).height - bottomSheet.top - binding.nav.height).toFloat()
                    } else {
                        if (viewModel.chosenItems.value.isNotEmpty()) {
                            behavior.isHideable = false
                            showDialog {
                                behavior.isHideable = true
                            }
                        }
                    }
                    if (slideOffset < 1) {
//                        if (binding.appBarLayout.isVisible) {
//                            binding.appBarLayout.invisible(false)
////                            binding.llDragHandle.invisible(true)
//                        }
                    }
                }
            }.apply {
                binding.root.post {onSlide(binding.root.parent as View, 0f)}
            })
        }
    }

    private fun getMediaUris(): List<MediaPath> {
        val galleryMediaUrls = mutableListOf<MediaPath>()
        val imageColumns = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_MODIFIED)
        val videoColumns = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DATE_MODIFIED, MediaStore.Video.Media.DURATION)

        context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns,
            null, null, null
        )?.use { imageCursor ->
            val idColumn = imageCursor.getColumnIndex(MediaStore.Images.Media._ID)
            val dateColumn = imageCursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)
            while (imageCursor.moveToNext()) {
                val id = imageCursor.getLong(idColumn)
                val date = imageCursor.getLong(dateColumn).toDate()
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id).toString()
                galleryMediaUrls.add(MediaPath(uri, date = date))
            }
        }

        context?.contentResolver?.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns,
            null, null, null
        )?.use { videoCursor ->
            val idColumn = videoCursor.getColumnIndex(MediaStore.Video.Media._ID)
            val dateColumn = videoCursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED)
            val durationColumn = videoCursor.getColumnIndex(MediaStore.Video.Media.DURATION)
            while (videoCursor.moveToNext()) {
                val id = videoCursor.getLong(idColumn)
                val date = videoCursor.getLong(dateColumn).toDate()
                val duration = videoCursor.getLong(durationColumn)
                val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id).toString()
                galleryMediaUrls.add(MediaPath(uri, true, date, duration))
            }
        }
        return galleryMediaUrls.sortedByDescending { it.date }
    }

    private fun setUpOutsideTouchListener() {
        val outsideView = dialog?.window?.decorView?.findViewById<View>(com.google.android.material.R.id.touch_outside)
        outsideView?.setOnClickListener {
            if (viewModel.chosenItems.value.isNotEmpty()) {
                showDialog()
            } else {
                dialog?.dismiss()
            }
        }
    }

    private fun showDialog(callBack: () -> Unit = {}) {
        alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Do you want to discard?")
            .setNegativeButton("Cancel") {p0, p1 ->
                callBack.invoke()
            }
            .setPositiveButton("Discard"
            ) { p0, p1 ->
                viewModel.clearList()
                dialog?.dismiss()
            }
        alertDialog?.show()
    }

    override fun onResume() {
        super.onResume()
        setFreshRate(BOTTOM_SHEET_RATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        setFreshRate(defaultRefreshRate)
    }

    private fun setFreshRate(refreshRate: Float) {
        val window = requireActivity().window
        val layoutParams = window.attributes
        layoutParams.preferredRefreshRate = refreshRate
        window.attributes = layoutParams
    }

    private fun getDefaultRefreshRate() {
        if (defaultRefreshRate != 0f) return
        val display = (context?.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager).getDisplay(Display.DEFAULT_DISPLAY)
        Logger.log("freshRate = ${display.refreshRate}")
        defaultRefreshRate = display.refreshRate
    }

    companion object {
        private const val BOTTOM_SHEET_RATE = 60f
    }
}