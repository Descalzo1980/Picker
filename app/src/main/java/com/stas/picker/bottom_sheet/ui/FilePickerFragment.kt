package com.stas.picker.bottom_sheet.ui

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stas.picker.FileRepository
import com.stas.picker.FileRepositoryImpl
import com.stas.picker.Logger
import com.stas.picker.bottom_sheet.media_adapter.FileCategoryAdapter
import com.stas.picker.bottom_sheet.media_adapter.RecyclerItemDecoration
import com.stas.picker.databinding.FragmentFilePickerBinding
import com.stas.picker.model.EMPTY_STRING
import com.stas.picker.room.DatabaseBuilder
import com.stas.picker.room.FileItem
import com.stas.picker.utils.checkFileType
import com.stas.picker.utils.collectFlowLatest
import com.stas.picker.view_model.FileViewModel
import java.io.File


class FilePickerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilePickerBinding

    private var viewModel: FileViewModel? = null

    var db: FileRepository? = null

    private var fileAdapterType: FileCategoryAdapter? = null

    private var contentLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectImage(it)
        }
    }

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

        fileAdapterType = FileCategoryAdapter(object : FileCategoryAdapter.FileListener {
            override fun onClick(item: View) {
                if (item.visibility == View.VISIBLE) {
                    item.visibility = View.INVISIBLE
                }else{
                    item.visibility = View.VISIBLE
                }
            }
        })
        binding.rvFilePicker.apply {
            this.adapter = fileAdapterType
        }

        binding.btnInsert.setOnClickListener {
            contentLauncher.launch(TYPE_ALL)
        }
        collectFlowLatest(viewModel!!.listItems) { fileItems ->
            Logger.log("fileItemsSize = ${fileItems}")
            fileAdapterType?.submitList(fileItems)
        }
        viewModel?.getAllFiles()
        Logger.log("fileItemsSize = ${viewModel?.getAllFiles()}")

    }


    private fun selectImage(uri: Uri) {
        Logger.log(uri.toString())
        val sourceFile = DocumentFile.fromSingleUri(requireContext(), uri)
        sourceFile?.let { document ->
            val fileSize = document.length()
            val fileName = document.name
            val mimeType = getFileMimeType(uri)
            val fileCategory = checkFileType(mimeType)
            viewModel?.insertFile(
                FileItem(
                    uri = uri.toString(),
                    size = fileSize.toFloat(),
                    name = fileName ?: EMPTY_STRING,
                    extension = mimeType ?: EMPTY_STRING
                )
            )
        }
    }

    private fun getFileMimeType(uri: Uri): String? {
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(requireContext().contentResolver.getType(uri))
        } else {
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path!!)).toString())
        }
    }

    companion object {
        private const val TYPE_ALL = "*/*"
    }

}