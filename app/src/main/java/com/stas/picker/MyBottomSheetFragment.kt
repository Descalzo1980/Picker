package com.stas.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stas.picker.databinding.FragmentMyBottomSheetBinding


class MyBottomSheetFragment : BottomSheetDialogFragment() {

    private var binding: FragmentMyBottomSheetBinding? = null
    private lateinit var addContentAdapter: AddContentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAddContentAdapter()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpAddContentAdapter() {
        val addContentList = mutableListOf<AddContentModel>()
        addContentList.add(AddContentModel(AddContentType.GALLERY, isEnabled = true))
        addContentList.add(AddContentModel(AddContentType.FILE, isEnabled = true))
        addContentList.add(AddContentModel(AddContentType.LOCATION, isEnabled = true))

        addContentAdapter = AddContentAdapter { itemType ->
            when (itemType) {
                AddContentType.GALLERY -> {

                }

                AddContentType.FILE -> {

                }

                AddContentType.LOCATION -> {

                }
            }
        }
        addContentAdapter.submitListAdapter(addContentList)
    }

    private fun setUpRecyclerView() {
        binding?.rvAddContent?.apply {
            val spanCount = PickerFragment.LENGTH_TYPES
            val layoutManager =
                GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return PickerFragment.DIVIDER_SPAN
                }
            }
            this.layoutManager = layoutManager
            itemAnimator = null
            adapter = addContentAdapter
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}