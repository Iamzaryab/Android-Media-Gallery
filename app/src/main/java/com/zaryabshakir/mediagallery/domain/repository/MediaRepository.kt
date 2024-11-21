package com.zaryabshakir.mediagallery.domain.repository

import com.zaryabshakir.mediagallery.data.models.Bucket
import com.zaryabshakir.mediagallery.data.models.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getAllImageBuckets(): Flow<List<Bucket>>
    fun getAllVideoBuckets(): Flow<List<Bucket>>
    fun getAllImages(mediaId: String): Flow<List<Media>>
    fun getAllVideos(mediaId: String): Flow<List<Media>>

}