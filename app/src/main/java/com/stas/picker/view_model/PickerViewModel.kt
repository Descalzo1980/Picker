package com.stas.picker.view_model

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
        if (mediaItem.choosePosition == 0) {
            chooseItem(mediaItem)
        } else {
            removeItem(mediaItem)
        }
    }

    private fun chooseItem(item: MediaItem) {
        item.choosePosition = getIndex()
        _listItems.value = _listItems.value.map {
            if (it.uri == item.uri) {
                it.copy(choosePosition = item.choosePosition)
            } else {
                it
            }
        }
        _chosenItems.value.add(item)
    }

    private fun removeItem(item: MediaItem) {
        var counter = 0
        if (_chosenItems.value.size < 2) {
            _chosenItems.value.remove(item)
            _listItems.value = _listItems.value.map {
                if (it.uri == item.uri) {
                    it.copy(choosePosition = 0)
                } else {
                    it
                }
            }
        }
        _chosenItems.value.remove(item)
        _chosenItems.value.map { it.choosePosition = 0 }
        _chosenItems.value.map { it.choosePosition = counter++ }
        _listItems.value = _listItems.value.map {
            if (it.uri == item.uri) {
                it.copy(choosePosition = 0)
            } else {
                it
            }
        }
        _listItems.value = _listItems.value.map {
            if (_chosenItems.value.contains(it)) {
                val index = _chosenItems.value.indexOf(it)
                val item = _chosenItems.value[index]
                it.copy(choosePosition = item.choosePosition)
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






