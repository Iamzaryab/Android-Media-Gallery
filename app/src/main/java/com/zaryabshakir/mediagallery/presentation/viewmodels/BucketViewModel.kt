package com.zaryabshakir.mediagallery.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.zaryabshakir.mediagallery.domain.GetAllBucketsUseCase
import com.zaryabshakir.mediagallery.presentation.events.BucketIntent
import com.zaryabshakir.mediagallery.presentation.events.BucketUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BucketViewModel @Inject constructor(
    private val bucketsUseCase: GetAllBucketsUseCase
) : BaseViewModel<BucketUIEvent, BucketIntent>(BucketUIEvent.Loading) {

    override fun onEvent(screenEvent: BucketIntent) {
        when (screenEvent) {
            is BucketIntent.FetchBuckets -> getAllBuckets(screenEvent.mediaType)
        }
    }

    private fun getAllBuckets(mediaType: String) {
        bucketsUseCase.getAllBuckets(mediaType).onEach { list ->
            sendUIEvent(BucketUIEvent.OnFetchBuckets(list))
        }.launchIn(viewModelScope)
    }
}
