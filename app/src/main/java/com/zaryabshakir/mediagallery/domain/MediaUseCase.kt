package com.zaryabshakir.mediagallery.domain

import com.zaryabshakir.mediagallery.data.repository.MediaRepository
import com.zaryabshakir.mediagallery.uimodel.BucketUIDataModel
import com.zaryabshakir.mediagallery.uimodel.MediaUIDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaUseCase @Inject constructor(private val repository: MediaRepository) {
    fun getAllMedia(bucketUIDataModel: BucketUIDataModel): Flow<List<MediaUIDataModel>> =
        if (bucketUIDataModel.isVideo()) fetchAllVideos(
            bucketUIDataModel.getId().toString()
        ) else fetchAllImages(bucketUIDataModel.getId().toString())

    private fun fetchAllImages(mediaId: String): Flow<List<MediaUIDataModel>> {
        return repository.getAllImages(mediaId).map { mediaList ->
            mediaList.map { media ->
                MediaUIDataModel(media)
            }
        }
    }

    private fun fetchAllVideos(mediaId: String): Flow<List<MediaUIDataModel>> {
        return repository.getAllVideos(mediaId).map { mediaList ->
            mediaList.map { media ->
                MediaUIDataModel(media)
            }
        }
    }
}