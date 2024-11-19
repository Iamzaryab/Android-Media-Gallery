package com.zaryabshakir.mediagallery.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiState, ScreenEvent> : ViewModel() {

    private val _state = Channel<UiState>()
    val state = _state.receiveAsFlow()

    abstract fun onEvent(screenEvent: ScreenEvent)

    protected fun sendUIEvent(uiState: UiState) {
        viewModelScope.launch {
            _state.send(uiState)
        }
    }

}