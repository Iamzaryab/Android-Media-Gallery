package com.zaryabshakir.mediagallery.presentation.events

import com.zaryabshakir.mediagallery.constants.MediaType

sealed class BucketUIEvent {
    data class OnFetchBuckets(val buckets) : BucketUIEvent()
}
