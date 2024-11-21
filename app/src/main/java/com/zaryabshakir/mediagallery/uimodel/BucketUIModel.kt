package com.zaryabshakir.mediagallery.uimodel

import android.net.Uri

interface BucketUIModel {
    fun getId(): Long
    fun getName(): String
    fun getThumbnailUri(): Uri
    fun isVideo(): Boolean
}