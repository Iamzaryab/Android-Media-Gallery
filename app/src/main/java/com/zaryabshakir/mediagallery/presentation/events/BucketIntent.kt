package com.zaryabshakir.mediagallery.presentation.events


sealed class BucketIntent {
    data class FetchBuckets(val mediaType: String) : BucketIntent()
}
