package com.stas.picker.bottom_sheet.view_model

import androidx.lifecycle.ViewModel
import com.stas.picker.model.MediaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PickerViewModel : ViewModel() {

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

    fun clearList() {
        _chosenItems.update {
            emptyList()
        }
        _listItems.update {
            emptyList()
        }
    }

    private fun getIndex(): Int = _chosenItems.value.size + 1

}






