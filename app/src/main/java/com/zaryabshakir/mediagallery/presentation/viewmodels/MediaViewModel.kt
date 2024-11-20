package com.zaryabshakir.mediagallery.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.zaryabshakir.mediagallery.domain.MediaUseCase
import com.zaryabshakir.mediagallery.presentation.events.MediaIntent
import com.zaryabshakir.mediagallery.presentation.events.MediaUIEvent
import com.zaryabshakir.mediagallery.uimodel.BucketUIDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaUseCase: MediaUseCase
) : BaseViewModel<MediaUIEvent, MediaIntent>(MediaUIEvent.Loading) {

    override fun onEvent(screenEvent: MediaIntent) {
        when (screenEvent) {
            is MediaIntent.FetchMedia -> getAllMedia(screenEvent.bucket)
        }
    }

    private fun getAllMedia(bucket: BucketUIDataModel) {
        mediaUseCase.getAllMedia(bucket).onEach { list ->
            sendUIEvent(MediaUIEvent.OnFetchMedia(list))
        }.launchIn(viewModelScope)
    }
}
