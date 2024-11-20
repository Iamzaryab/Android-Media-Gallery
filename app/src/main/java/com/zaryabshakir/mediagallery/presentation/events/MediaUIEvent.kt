package com.zaryabshakir.mediagallery.presentation.events

import com.zaryabshakir.mediagallery.uimodel.MediaUIDataModel

sealed class MediaUIEvent {
    data object Loading : MediaUIEvent()
    data class OnFetchMedia(val media: List<MediaUIDataModel>) : MediaUIEvent()
}
