package com.zaryabshakir.mediagallery.uimodel

import android.net.Uri
import android.os.Parcelable
import com.zaryabshakir.mediagallery.data.models.Bucket
import kotlinx.parcelize.Parcelize

@Parcelize
data class BucketUIDataModel(private val bucket: Bucket) : BucketUIModel, Parcelable {
    override fun getId(): Long = bucket.id
    override fun getName(): String = bucket.name
    override fun getThumbnailUri(): Uri = bucket.thumbnailUri
    override fun isVideo(): Boolean = bucket.isVideo
}