package com.zaryabshakir.mediagallery.presentation.events

import com.zaryabshakir.mediagallery.uimodel.BucketUIDataModel

sealed class BucketUIEvent {
    data object Loading : BucketUIEvent()
    data class OnFetchBuckets(val buckets: List<BucketUIDataModel>) : BucketUIEvent()
}
