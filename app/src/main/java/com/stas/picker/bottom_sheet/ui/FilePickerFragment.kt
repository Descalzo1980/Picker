package com.stas.picker.bottom_sheet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.stas.picker.FileRepository
import com.stas.picker.FileRepositoryImpl
import com.stas.picker.Logger
import com.stas.picker.databinding.FragmentFilePickerBinding
import com.stas.picker.room.DatabaseBuilder
import com.stas.picker.room.FileItem
import com.stas.picker.utils.collectFlowLatest
import com.stas.picker.view_model.FileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FilePickerFragment : Fragment() {

    private lateinit var binding: FragmentFilePickerBinding

    private var viewModel: FileViewModel? = null

    var db: FileRepository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilePickerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FileRepositoryImpl(DatabaseBuilder.getInstance(requireContext()))
        viewModel = FileViewModel(db!!)
        binding.btnInsert.setOnClickListener {
            viewModel!!.insertFile(FileItem("1", 1f, "OLOLO", ".pdf"))
        }
        collectFlowLatest(viewModel!!.listItems) {
            Logger.log(it.toString())
        }
    }

}