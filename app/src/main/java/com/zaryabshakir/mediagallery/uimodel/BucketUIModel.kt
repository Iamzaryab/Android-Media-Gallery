package com.zaryabshakir.mediagallery.uimodel

import android.net.Uri

interface BucketUIModel {
    fun getId(): Long
    fun getName(): String
    fun thumbnailUri(): Uri
}