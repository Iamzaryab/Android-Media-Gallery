package com.zaryabshakir.mediagallery.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiState, ScreenEvent>(initialState: UiState) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    abstract fun onEvent(screenEvent: ScreenEvent)

    protected fun sendUIEvent(uiState: UiState) {
        viewModelScope.launch {
            _state.emit(uiState)
        }
    }

}