package com.zaryabshakir.mediagallery.domain

import com.zaryabshakir.mediagallery.constants.MediaType
import com.zaryabshakir.mediagallery.data.repository.MediaRepository
import javax.inject.Inject

class GetAllMediaUseCase @Inject constructor(private val repository: MediaRepository) {
    fun getAllBuckets(mediaType: MediaType){
        when(mediaType){
            MediaType.IMAGE->{

            }
            MediaType.VIDEO->{

            }
        }
    }
}