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
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaRepository {
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

                val idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID).takeIf { it != -1 }
                    ?: return@use
                val bucketIdIndex =
                    cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID).takeIf { it != -1 }
                        ?: return@use
                val bucketDisplayNameIndex =
                    cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                        .takeIf { it != -1 } ?: return@use
                val dateModifiedIndex =
                    cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED).takeIf { it != -1 }
                        ?: return@use

                val buckets = mutableMapOf<Long, Bucket>()
                val allImageMediaBucket = mutableListOf<Bucket>()

                do {
                    try {
                        val id = cursor.getLong(idIndex)
                        val bucketId = cursor.getLong(bucketIdIndex)
                        val bucketName = cursor.getString(bucketDisplayNameIndex).orEmpty()
                        val dateModified = cursor.getLong(dateModifiedIndex)
                        val thumbnailUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id,
                        )
                        if (!buckets.containsKey(bucketId)) {
                            buckets[bucketId] = Bucket(
                                id = bucketId,
                                name = bucketName,
                                dateModified = dateModified,
                                thumbnailUri = thumbnailUri,
                                isVideo = false
                            )
                        }

                        allImageMediaBucket.add(
                            Bucket(
                                id = id,
                                name = "All",
                                dateModified = dateModified,
                                thumbnailUri = thumbnailUri,
                                isVideo = false
                            )
                        )
                    } catch (e: Throwable) {
                        Log.e("getAllImageBuckets", "Error processing bucket data", e)
                    }
                } while (cursor.moveToNext())

                val result = mutableListOf<Bucket>()

                allImageMediaBucket.firstOrNull()?.thumbnailUri?.let {
                    result.add(
                        Bucket(
                            id = -1,
                            name = "All",
                            dateModified = System.currentTimeMillis(),
                            thumbnailUri = it,
                            isVideo = false
                        )
                    )
                }

                result.addAll(buckets.values.sortedBy { it.name })

                emit(result)
            } ?: emit(emptyList())
        } catch (e: Throwable) {
            Log.e("getAllImageBuckets", "Error querying image buckets", e)
            emit(emptyList())
        }
    }


    override fun getAllVideoBuckets(): Flow<List<Bucket>> = flow {
        try {
            val query = context.contentResolver.query(
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
            )

            query?.use { cursor ->
                if (!cursor.moveToFirst()) {
                    emit(emptyList())
                    return@use
                }

                val idIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID).takeIf { it != -1 }
                    ?: return@use
                val bucketIdIndex =
                    cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID).takeIf { it != -1 }
                        ?: return@use
                val bucketDisplayNameIndex =
                    cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
                        .takeIf { it != -1 } ?: return@use
                val dateModifiedIndex =
                    cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED).takeIf { it != -1 }
                        ?: return@use

                val buckets = mutableMapOf<Long, Bucket>()
                val allMediaBucket = mutableListOf<Bucket>()

                do {
                    try {
                        val id = cursor.getLong(idIndex)
                        val bucketId = cursor.getLong(bucketIdIndex)
                        val bucketName = cursor.getString(bucketDisplayNameIndex).orEmpty()
                        val dateModified = cursor.getLong(dateModifiedIndex)
                        val thumbnailUri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            id
                        )

                        if (!buckets.containsKey(bucketId)) {
                            buckets[bucketId] = Bucket(
                                id = bucketId,
                                name = bucketName,
                                dateModified = dateModified,
                                thumbnailUri = thumbnailUri,
                                isVideo = true
                            )
                        }

                        allMediaBucket.add(
                            Bucket(
                                id = id,
                                name = "All",
                                dateModified = dateModified,
                                thumbnailUri = thumbnailUri,
                                isVideo = true
                            )
                        )
                    } catch (e: Throwable) {
                        Log.e("getAllVideoBuckets", "Error processing bucket data", e)
                    }
                } while (cursor.moveToNext())

                val result = mutableListOf<Bucket>()

                allMediaBucket.firstOrNull()?.thumbnailUri?.let {
                    result.add(
                        Bucket(
                            id = -1,
                            name = "All",
                            dateModified = System.currentTimeMillis(),
                            thumbnailUri = it,
                            isVideo = true
                        )
                    )
                }

                result.addAll(buckets.values.sortedBy { it.name })

                emit(result)
            } ?: emit(emptyList())
        } catch (e: Throwable) {
            Log.e("getAllVideoBuckets", "Error querying video buckets", e)
            emit(emptyList())
        }
    }

    override fun getAllImages(mediaId: String): Flow<List<Media>> = flow {
        try {
            val selection: String?
            val selectionArgs: Array<String>?

            if (mediaId == "-1") {
                selection =
                    "${MediaStore.Images.Media.SIZE} > 0 AND ${MediaStore.Images.Media.MIME_TYPE} NOT LIKE ?"
                selectionArgs = arrayOf("image/%cache%")
            } else {
                selection =
                    "${MediaStore.Images.Media.BUCKET_ID} = ? AND ${MediaStore.Images.Media.SIZE} > 0 AND ${MediaStore.Images.Media.MIME_TYPE} NOT LIKE ?"
                selectionArgs = arrayOf(mediaId, "image/%cache%")
            }

            val query = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.TITLE,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.SIZE,
                    MediaStore.Images.Media.MIME_TYPE,
                    MediaStore.Images.Media.DATE_MODIFIED,
                ),
                selection,
                selectionArgs,
                "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
            )

            query?.use { cursor ->
                if (!cursor.moveToFirst()) {
                    emit(emptyList())
                    return@use
                }

                val mediaIdIndex =
                    cursor.getColumnIndex(MediaStore.Images.Media._ID).takeIf { it != -1 }
                        ?: return@use
                val mediaTitleIndex =
                    cursor.getColumnIndex(MediaStore.Images.Media.TITLE).takeIf { it != -1 }
                        ?: return@use
                val mediaDisplayNameIndex =
                    cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME).takeIf { it != -1 }
                        ?: return@use
                val mediaSizeIndex =
                    cursor.getColumnIndex(MediaStore.Images.Media.SIZE).takeIf { it != -1 }
                        ?: return@use
                val mediaMimeTypeIndex =
                    cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE).takeIf { it != -1 }
                        ?: return@use
                val mediaDateModifiedIndex =
                    cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED).takeIf { it != -1 }
                        ?: return@use

                val mediaMap = mutableMapOf<Long, Media>()

                do {
                    try {
                        val id = cursor.getLong(mediaIdIndex)
                        val title = cursor.getString(mediaTitleIndex).orEmpty()
                        val displayName = cursor.getString(mediaDisplayNameIndex).orEmpty()
                        val dateModified = cursor.getLong(mediaDateModifiedIndex)
                        val size = cursor.getLong(mediaSizeIndex)
                        val mimeType = cursor.getString(mediaMimeTypeIndex).orEmpty()

                        if (!mediaMap.containsKey(id)) {
                            mediaMap[id] = Media(
                                id = id,
                                title = title,
                                displayName = displayName,
                                dateModified = dateModified,
                                mimeType = mimeType,
                                size = size,
                                uri = ContentUris.withAppendedId(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    id
                                ),
                                isVideo = false
                            )
                        }
                    } catch (e: Throwable) {
                        Log.e("getAllImageAlbums", "Error processing album data", e)
                    }
                } while (cursor.moveToNext())

                val result = mediaMap.values.sortedBy { it.title }
                emit(result)
            } ?: emit(emptyList())
        } catch (e: Throwable) {
            Log.e("getAllImageAlbums", "Error querying image albums", e)
            emit(emptyList())
        }
    }

    override fun getAllVideos(mediaId: String): Flow<List<Media>> = flow {
        try {
            val selection: String?
            val selectionArgs: Array<String>?

            if (mediaId == "-1") {
                selection = "${MediaStore.Video.Media.SIZE} > 0 AND ${MediaStore.Video.Media.MIME_TYPE} NOT LIKE ?"
                selectionArgs = arrayOf("video/%cache%")
            } else {
                selection = "${MediaStore.Video.Media.BUCKET_ID} = ? AND ${MediaStore.Video.Media.SIZE} > 0 AND ${MediaStore.Video.Media.MIME_TYPE} NOT LIKE ?"
                selectionArgs = arrayOf(mediaId, "video/%cache%")
            }

            val query = context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.MIME_TYPE,
                    MediaStore.Video.Media.DATE_MODIFIED
                ),
                selection,
                selectionArgs,
                "${MediaStore.Video.Media.DATE_MODIFIED} DESC"
            )

            query?.use { cursor ->
                if (!cursor.moveToFirst()) {
                    emit(emptyList())
                    return@use
                }

                val idIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID).takeIf { it != -1 } ?: return@use
                val titleIndex = cursor.getColumnIndex(MediaStore.Video.Media.TITLE).takeIf { it != -1 } ?: return@use
                val displayNameIndex = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME).takeIf { it != -1 } ?: return@use
                val sizeIndex = cursor.getColumnIndex(MediaStore.Video.Media.SIZE).takeIf { it != -1 } ?: return@use
                val mimeTypeIndex = cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE).takeIf { it != -1 } ?: return@use
                val dateModifiedIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED).takeIf { it != -1 } ?: return@use

                val videoList = mutableListOf<Media>()

                do {
                    try {
                        val id = cursor.getLong(idIndex)
                        val title = cursor.getString(titleIndex).orEmpty()
                        val displayName = cursor.getString(displayNameIndex).orEmpty()
                        val dateModified = cursor.getLong(dateModifiedIndex)
                        val size = cursor.getLong(sizeIndex)
                        val mimeType = cursor.getString(mimeTypeIndex).orEmpty()
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            id
                        )

                        videoList.add(
                            Media(
                                id = id,
                                title = title,
                                displayName = displayName,
                                dateModified = dateModified,
                                mimeType = mimeType,
                                size = size,
                                uri = uri,
                                thumbnailUri = uri,
                                isVideo = true
                            )
                        )
                    } catch (e: Throwable) {
                        Log.e("getAllVideos", "Error processing video data", e)
                    }
                } while (cursor.moveToNext())

                val result = videoList.sortedBy { it.title }
                emit(result)
            } ?: emit(emptyList())
        } catch (e: Throwable) {
            Log.e("getAllVideos", "Error querying videos", e)
            emit(emptyList())
        }
    }
}