package com.stas.picker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PictureViewModel : ViewModel() {

    private val _counterFlow = MutableStateFlow(0)
    val counterFlow: StateFlow<Int> = _counterFlow

    fun incrementCounter() {
        if (_counterFlow.value < 30) {
            _counterFlow.value++
        }
    }

    fun decrementCounter() {
        if (_counterFlow.value > 0) {
            _counterFlow.value--
        }
    }
}






