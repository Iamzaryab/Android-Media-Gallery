package com.zaryabshakir.mediagallery.domain.usecase

import com.zaryabshakir.mediagallery.constants.MediaType
import com.zaryabshakir.mediagallery.domain.repository.MediaRepository
import com.zaryabshakir.mediagallery.uimodel.BucketUIDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllBucketsUseCase @Inject constructor(private val repository: MediaRepository) {
    fun getAllBuckets(mediaType: String): Flow<List<BucketUIDataModel>> = when (mediaType) {
        MediaType.IMAGE.name -> {
            repository.getAllImageBuckets()
                .map { bucketList ->
                    bucketList.map { bucket ->
                        BucketUIDataModel(bucket)
                    }
                }
        }

        MediaType.VIDEO.name -> {
            repository.getAllVideoBuckets()
                .map { bucketList -> bucketList.map { bucket -> BucketUIDataModel(bucket) } }
        }

        else -> flowOf()
    }
}