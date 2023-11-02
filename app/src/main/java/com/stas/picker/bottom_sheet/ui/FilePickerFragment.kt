package com.stas.picker.bottom_sheet.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.documentfile.provider.DocumentFile
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stas.picker.FileRepository
import com.stas.picker.FileRepositoryImpl
import com.stas.picker.Logger
import com.stas.picker.bottom_sheet.file_adapter.FileCategoryAdapter
import com.stas.picker.databinding.FragmentFilePickerBinding
import com.stas.picker.model.EMPTY_STRING
import com.stas.picker.room.DatabaseBuilder
import com.stas.picker.room.FileItem
import com.stas.picker.utils.collectFlowLatest
import com.stas.picker.utils.exists
import com.stas.picker.utils.visible
import com.stas.picker.bottom_sheet.view_model.FileViewModel
import com.stas.picker.utils.PaddingDecorator
import java.io.File


class FilePickerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilePickerBinding

    private var viewModel: FileViewModel? = null

    private var db: FileRepository? = null

    private var fileAdapterType: FileCategoryAdapter? = null

    private var contentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            val uri: Uri = activityResult.data?.data ?: return@registerForActivityResult
            val contentResolver = activity?.contentResolver
            contentResolver?.takePersistableUriPermission(uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION)
            contentResolver?.takePersistableUriPermission(uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            selectImage(uri)
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
                item.visible(item.isVisible.not())
            }
        })
        binding.rvFilePicker.apply {
            this.adapter = fileAdapterType
            this.addItemDecoration(
                PaddingDecorator(
                    16
                )
            )
        }

        binding.btnInsert.setOnClickListener {
            startIntent()
        }
        collectFlowLatest(viewModel!!.listItems) { fileItems ->
            Logger.log("fileItemsSize = $fileItems")
            fileAdapterType?.submitList(
                fileItems.filter {
                   it.exists(requireContext())
                }
            )
        }
        viewModel?.getAllFiles()
        Logger.log("fileItemsSize = ${viewModel?.getAllFiles()}")

    }

    private fun startIntent() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = TYPE_ALL
        }
        contentLauncher.launch(intent)
    }


    private fun selectImage(uri: Uri) {
        val sourceFile = DocumentFile.fromSingleUri(requireContext(), uri)
        sourceFile?.let { document ->
            val fileSize = document.length()
            val fileName = document.name
            val mimeType = getFileMimeType(uri)
            viewModel?.insertFile(
                FileItem(
                    uri = uri.toString(),
                    size = fileSize,
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