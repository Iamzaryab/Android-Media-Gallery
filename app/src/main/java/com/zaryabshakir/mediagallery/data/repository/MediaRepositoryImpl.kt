package com.zaryabshakir.mediagallery.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.zaryabshakir.mediagallery.data.models.Bucket
import com.zaryabshakir.mediagallery.data.models.Media
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaRepository {
//    override fun getAllImageBuckets(): Flow<List<Bucket>> = flow {
//        try {
//            context.contentResolver.query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                arrayOf(
//                    MediaStore.Images.Media.BUCKET_ID,
//                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
//                    MediaStore.Images.Media._ID,
//                    MediaStore.Images.Media.DATE_MODIFIED,
//                ),
//                null,
//                null,
//                "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
//            )?.use {
//                if (it.moveToFirst()) {
//                    val idIndex = it.getColumnIndex(MediaStore.Images.Media._ID)
//                    val bucketIdIndex = it.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)
//                    val bucketDisplayNameIndex =
//                        it.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
//                    val dateModifiedIndex = it.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)
//
//                    val buckets = mutableMapOf<Long, Bucket>()
//
//                    do {
//                        try {
//                            val bucketId = it.getLong(bucketIdIndex)
//                            if (buckets.containsKey(bucketId)) continue
//
//                            buckets[bucketId] = Bucket(
//                                id = bucketId,
//                                name = it.getString(bucketDisplayNameIndex),
//                                dateModified = it.getLong(dateModifiedIndex),
//                                thumbnailUri = ContentUris.withAppendedId(
//                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                    it.getLong(idIndex),
//                                ),
//                                isVideo = false
//                            )
//                        } catch (e: Throwable) {
//                            e.printStackTrace()
//                        }
//                    } while (it.moveToNext())
//
//                    val result = buckets.values.toMutableList()
//                    result.sortBy { bucket -> bucket.name }
//
//                    emit(result)
//                }
//            }
//        } catch (e: Throwable) {
//            e.printStackTrace()
//            emit(listOf())
//        }
//
//    }
override fun getAllImageBuckets(): Flow<List<Bucket>> = flow {
    try {
        val query = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_MODIFIED,
            ),
            null,
            null,
            "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
        )

        query?.use { cursor ->
            if (!cursor.moveToFirst()) {
                emit(emptyList())
                return@use
            }

            val idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID).takeIf { it != -1 } ?: return@use
            val bucketIdIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID).takeIf { it != -1 } ?: return@use
            val bucketDisplayNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME).takeIf { it != -1 } ?: return@use
            val dateModifiedIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED).takeIf { it != -1 } ?: return@use

            val buckets = mutableMapOf<Long, Bucket>()

            do {
                try {
                    val bucketId = cursor.getLong(bucketIdIndex)
                    if (buckets.containsKey(bucketId)) continue

                    buckets[bucketId] = Bucket(
                        id = bucketId,
                        name = cursor.getString(bucketDisplayNameIndex).orEmpty(),
                        dateModified = cursor.getLong(dateModifiedIndex),
                        thumbnailUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(idIndex),
                        ),
                        isVideo = false
                    )
                } catch (e: Throwable) {
                    Log.e("getAllImageBuckets", "Error processing bucket data", e)
                }
            } while (cursor.moveToNext())

            val result = buckets.values.sortedBy { it.name.orEmpty() }
            emit(result)
        } ?: emit(emptyList())
    } catch (e: Throwable) {
        Log.e("getAllImageBuckets", "Error querying image buckets", e)
        emit(emptyList())
    }
}


    override fun getAllVideoBuckets(): Flow<List<Bucket>> = flow {
        try {
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Video.Media.BUCKET_ID,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATE_MODIFIED,
                ),
                null,
                null,
                "${MediaStore.Video.Media.DATE_MODIFIED} DESC"
            )?.use {
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex(MediaStore.Video.Media._ID)
                    val bucketIdIndex = it.getColumnIndex(MediaStore.Video.Media.BUCKET_ID)
                    val bucketDisplayNameIndex =
                        it.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
                    val dateModifiedIndex = it.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED)

                    val buckets = mutableMapOf<Long, Bucket>()

                    do {
                        try {
                            val bucketId = it.getLong(bucketIdIndex)
                            if (buckets.containsKey(bucketId)) continue

                            buckets[bucketId] = Bucket(
                                id = bucketId,
                                name = it.getString(bucketDisplayNameIndex),
                                dateModified = it.getLong(dateModifiedIndex),
                                thumbnailUri = ContentUris.withAppendedId(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    it.getLong(idIndex)
                                ),
                                isVideo = true

                            )
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    } while (it.moveToNext())

                    val result = buckets.values.toMutableList()
                    result.sortBy { bucket -> bucket.name }

                    emit(result)
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            emit(listOf())
        }

    }

    override fun getAllImages(bucket: Bucket): Flow<List<Media>> {
        return flowOf()
    }

    override fun getAllVideos(bucket: Bucket): Flow<List<Media>> {
        return flowOf()
    }
}