package com.stas.picker.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stas.picker.FileRepository
import com.stas.picker.room.FileItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FileViewModel(private val fileRepository: FileRepository) : ViewModel() {

    private var _listItems = MutableStateFlow<List<FileItem>>(emptyList())
    val listItems: StateFlow<List<FileItem>> = _listItems

    fun insertFile(fileItem: FileItem) {
        viewModelScope.launch {
            fileRepository.insertItem(fileItem)
            getAllFiles()
        }
    }
    suspend fun getAllFiles() {
        viewModelScope.launch {
            val list = fileRepository.getAllFiles()
            _listItems.update {
                list
            }
        }
    }

}