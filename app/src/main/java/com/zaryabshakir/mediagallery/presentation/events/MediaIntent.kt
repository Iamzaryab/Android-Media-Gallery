package com.zaryabshakir.mediagallery.presentation.events

import com.zaryabshakir.mediagallery.uimodel.BucketUIDataModel


sealed class MediaIntent {
    data class FetchMedia(val bucket: BucketUIDataModel) : MediaIntent()
}
