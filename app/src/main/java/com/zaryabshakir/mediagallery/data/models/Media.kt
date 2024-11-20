package com.zaryabshakir.mediagallery.data.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
    val id: Long,
    val title: String,
    val displayName: String,
    val size: Long,
    val mimeType: String,
    val dateModified: Long,
    val uri: Uri,
    val thumbnailUri: Uri? = null,
    val isVideo: Boolean
) : Parcelable