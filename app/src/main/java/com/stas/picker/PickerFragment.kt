package com.stas.picker

import android.content.ContentUris
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
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
        val bhd = MyBottomSheetFragment(this)
        binding?.showBottomSheetButton?.setOnClickListener {
            showSecondDialog()
        }

    }

    fun showSecondDialog() {
        val adapter = RecyclerViewAdapter(object : RecyclerViewAdapter.Listener {
            override fun onClick(chooseIndex: Int, callback: (Int) -> Unit) {
//                if (calculateIndex(chooseIndex)) {
//                    callback.invoke(chooseIndex)
//                } else {
//                    Unit
//                }
            }
        })
        binding?.showBottomSheetButton?.setOnClickListener {
            val dialog = KirillBottomSheet()
            dialog.list = test().toMediaItem()
            Log.d("MYTAGMYTAG", "list size = ${dialog.list.size}")
            val gridLayout = GridLayoutManager(requireContext(), 3)
            binding?.revMediaPicker?.adapter = adapter
            binding?.revMediaPicker?.layoutManager = gridLayout
            dialog.show(childFragmentManager, null)
        }
    }

    private fun calculateIndex(chooseIndex: Int): Boolean {
        return chooseIndex == 0
    }

    private fun test(): List<String> {
        val galleryImageUrls = mutableListOf<String>()
        val columns = arrayOf(MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN

        context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
            null, null, "$orderBy DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)

                galleryImageUrls.add(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id).toString())
            }
        }
        return galleryImageUrls
    }

    private fun List<String?>.toMediaItem(): List<RecyclerViewAdapter.Types.MediaItem> {
        var position = 0
        val result = mutableListOf<RecyclerViewAdapter.Types.MediaItem>()
        result.addAll(
            this.map {
                RecyclerViewAdapter.Types.MediaItem(it, 0, position++)
            }
        )
        return result
    }

    companion object {
        const val LENGTH_TYPES = 3
        const val DIVIDER_SPAN = 1
    }
}