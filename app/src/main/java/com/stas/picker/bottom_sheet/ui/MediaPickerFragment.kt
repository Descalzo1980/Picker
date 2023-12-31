package com.stas.picker.bottom_sheet.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.stas.picker.bottom_sheet.ui.media_preview.PhotoFragment
import com.stas.picker.PickerFragment
import com.stas.picker.R
import com.stas.picker.bottom_sheet.ui.media_preview.VideoFragment
import com.stas.picker.bottom_sheet.media_adapter.RecyclerItemDecoration
import com.stas.picker.bottom_sheet.media_adapter.RecyclerViewAdapter
import com.stas.picker.bottom_sheet.view_model.PickerViewModel
import com.stas.picker.databinding.FragmentMediaPickerBinding
import com.stas.picker.model.EMPTY_STRING
import com.stas.picker.model.MediaItem
import com.stas.picker.utils.extension.collectFlowLatest
import com.stas.picker.utils.extension.visible
import kotlinx.coroutines.flow.collectLatest

class MediaPickerFragment : Fragment() {

    private lateinit var binding: FragmentMediaPickerBinding
    private lateinit var viewModel: PickerViewModel
    private var viewAdapter: RecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaPickerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PickerViewModel::class.java]
        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        viewAdapter = RecyclerViewAdapter(object : RecyclerViewAdapter.Listener {
            override fun onMediaClick(mediaItem: MediaItem) {
                if (mediaItem.length != EMPTY_STRING) {
                    val dialog = VideoFragment()
                    dialog.arguments = bundleOf(PickerFragment.VIDEO_ITEM to mediaItem.uri)
                    dialog.show(childFragmentManager, null)
                } else {
                    val dialog = PhotoFragment()
                    dialog.arguments = bundleOf(PickerFragment.PHOTO_ITEM to mediaItem.uri)
                    dialog.show(childFragmentManager, null)
                }
            }

            override fun onPickItemClick(mediaItem: MediaItem) {
                viewModel.handleItemClick(mediaItem)
            }
        })

        val decorator = RecyclerItemDecoration(
            SPAN_COUNT,
            SPACING
        )
        binding.revMediaPicker.apply {
            this.adapter = viewAdapter
            setHasFixedSize(true)
            this.layoutManager = layoutManager
            addItemDecoration(decorator)

        }

        collectFlowLatest(viewModel.chosenItems) {
            Log.d("MYAGMYTAG", "currentChosenList = $it")
            binding.apply {
                if (it.isNotEmpty()) {
                    tvChooseCount.visible(true)
                    tvChooseCount.text = getString(R.string.bottom_sheet_chosen, it.size)
                } else {
                    tvChooseCount.visible(false)
                }
            }
        }

        collectFlowLatest(viewModel.listItems) {
            viewModel.listItems.collectLatest {
                Log.d("MYAGMYTAG", "currentList = $it")
                viewAdapter?.submitList(it)
            }
        }
    }

    companion object {
        const val SPAN_COUNT = 3
        const val SPACING = 2

        fun getInstance(): MediaPickerFragment = MediaPickerFragment()
    }
}