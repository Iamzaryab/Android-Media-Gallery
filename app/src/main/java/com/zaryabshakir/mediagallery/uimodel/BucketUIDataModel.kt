package com.zaryabshakir.mediagallery.uimodel

import android.net.Uri
import com.zaryabshakir.mediagallery.data.models.Bucket

class BucketUIDataModel(private val bucket: Bucket) : BucketUIModel {
    override fun getId(): Long = bucket.id

    override fun getName(): String = bucket.name

    override fun thumbnailUri(): Uri = bucket.thumbnailUri
}