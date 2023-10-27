package com.stas.picker.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.stas.picker.model.MediaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PickerViewModel : ViewModel() {

    private val _listItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val listItems: StateFlow<List<MediaItem>> = _listItems

    private val _chosenItems = MutableStateFlow<MutableList<MediaItem>>(mutableListOf())

    fun handleItemClick(mediaItem: MediaItem) {
        if (_chosenItems.value.size >= 30) {
            return
        }
        if (_chosenItems.value.contains(mediaItem).not()) {
            chooseItem(mediaItem)
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
        _chosenItems.value.add(item)
    }

    private fun removeItem(item: MediaItem) {
        var counter = 1
        _chosenItems.value.remove(item)
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
        _listItems.value = list
    }

    private fun getIndex(): Int = _chosenItems.value.size + 1

}






