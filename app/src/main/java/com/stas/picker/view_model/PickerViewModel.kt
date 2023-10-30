package com.stas.picker.view_model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stas.picker.FileRepository
import com.stas.picker.FileRepositoryImpl
import com.stas.picker.model.MediaItem
import com.stas.picker.room.AppDatabase
import com.stas.picker.room.FileItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PickerViewModel(val context: Context) : ViewModel() {

    private val dbHelper = FileRepositoryImpl(AppDatabase.getInstance(context))

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            dbHelper.getFile()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    // handle exception
                }
                .collect {
                    // list of users from the database
                }
        }
    }

    private val _listItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val listItems: StateFlow<List<MediaItem>> = _listItems

    private val _chosenItems = MutableStateFlow<List<String>>(mutableListOf())
    val chosenItems: StateFlow<List<String>> = _chosenItems

    fun handleItemClick(mediaItem: MediaItem) {
        if (_chosenItems.value.contains(mediaItem.uri).not()) {
            if (_chosenItems.value.size >= 10) {
                return
            } else {
                chooseItem(mediaItem)
            }
        } else {
            removeItem(mediaItem)
        }
    }

    private fun chooseItem(item: MediaItem) {
        _listItems.value = _listItems.value.map {
            if (it.uri == item.uri) {
                it.copy(choosePosition = getIndex())
            } else {
                it
            }
        }
        val list = _chosenItems.value.toMutableList()
        list.add(item.uri)
        _chosenItems.update {
            list
        }
    }

    private fun removeItem(item: MediaItem) {
        var counter = 1
        val list = _chosenItems.value.toMutableList()
        list.remove(item.uri)
        _chosenItems.update {
            list
        }
        _listItems.value = _listItems.value.map {
            if (it.uri == item.uri) {
                it.copy(choosePosition = 0)
            } else if (it.choosePosition > 0) {
                it.copy(choosePosition = counter++)
            } else {
                it
            }
        }
    }

    fun setList(list: List<MediaItem>) {
        _listItems.update {
            list
        }
    }

    private fun getIndex(): Int = _chosenItems.value.size + 1

}






