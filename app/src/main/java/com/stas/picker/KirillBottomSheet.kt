package com.stas.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.OnScrollListener
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stas.picker.databinding.BottomSheetBinding

class KirillBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetBinding
    lateinit var list: List<RecyclerViewAdapter.Types.MediaItem>
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.bind(inflater.inflate(R.layout.bottom_sheet, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spanCount = 3
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        val adapter = RecyclerViewAdapter(object : RecyclerViewAdapter.Listener {
            override fun onClick(chooseIndex: Int, callback: (Int) -> Unit) {
                if (calculateIndex(chooseIndex)) {
                    callback.invoke(chooseIndex)
                } else {
                    Unit
                }
            }
        })
        binding.revMediaPicker.adapter = adapter
        binding.revMediaPicker.layoutManager = layoutManager
        dialog?.layoutInflater
        adapter.submitList(list)
        dialog?.show()
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.peekHeight = (requireActivity().resources.displayMetrics.heightPixels * 0.5).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    TODO("Not yet implemented")
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun calculateIndex(chooseIndex: Int): Boolean {
        return chooseIndex == 0
    }
}