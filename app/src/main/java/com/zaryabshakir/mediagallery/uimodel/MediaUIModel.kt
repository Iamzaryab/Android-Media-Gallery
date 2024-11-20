package com.zaryabshakir.mediagallery.uimodel

import android.net.Uri

interface MediaUIModel {
    fun getId(): Long
    fun getTitle(): String
    fun getDisplayName(): String
    fun getMimeType(): String
    fun getDateModified(): Long
    fun getUri(): Uri
    fun isVideo(): Boolean
}