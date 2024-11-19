package com.zaryabshakir.mediagallery.data.repository

import com.zaryabshakir.mediagallery.data.models.Bucket
import com.zaryabshakir.mediagallery.data.models.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getAllImageBuckets(): Flow<List<Bucket>>
    fun getAllVideoBuckets(): Flow<List<Bucket>>
    fun getAllImages(bucket: Bucket): Flow<List<Media>>
    fun getAllVideos(bucket: Bucket): Flow<List<Media>>

}