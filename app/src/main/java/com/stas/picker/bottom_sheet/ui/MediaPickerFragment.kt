package com.stas.picker.bottom_sheet.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.stas.picker.PickerFragment
import com.stas.picker.R
import com.stas.picker.bottom_sheet.media_adapter.RecyclerItemDecoration
import com.stas.picker.bottom_sheet.media_adapter.RecyclerViewAdapter
import com.stas.picker.databinding.FragmentMediaPickerBinding
import com.stas.picker.model.MediaItem
import com.stas.picker.utils.collectFlowLatest
import com.stas.picker.utils.visible
import com.stas.picker.view_model.PickerViewModel
import kotlinx.coroutines.flow.collectLatest

class MediaPickerFragment : Fragment() {

    private lateinit var binding: FragmentMediaPickerBinding
    private lateinit var viewModel: PickerViewModel
    private var adapter: RecyclerViewAdapter? = null

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
        adapter = RecyclerViewAdapter(this, object : RecyclerViewAdapter.Listener {
            override fun onClick(mediaItem: MediaItem) {
                if (mediaItem.length > 0) {
                    findNavController().navigate(
                        R.id.action_pickerFragment_to_videoFragment,
                        bundleOf(
                            PickerFragment.VIDEO_ITEM to mediaItem.uri
                        )
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_pickerFragment_to_photoVideoFragment,
                        bundleOf(
                            PickerFragment.PHOTO_ITEM to mediaItem.uri
                        )
                    )
                }
            }

            override fun onLongClick(mediaItem: MediaItem) {
                viewModel.handleItemClick(mediaItem)
            }
        })

        val decorator = RecyclerItemDecoration(
            SPAN_COUNT,
            SPACING
        )
        binding.revMediaPicker.adapter = adapter
        binding.revMediaPicker.layoutManager = layoutManager
        binding.revMediaPicker.addItemDecoration(decorator)

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
                adapter?.submitList(it)
            }
        }
    }

    companion object {
        private const val SPAN_COUNT = 3
        private const val SPACING = 2

        fun getInstance(): MediaPickerFragment = MediaPickerFragment()
    }
}