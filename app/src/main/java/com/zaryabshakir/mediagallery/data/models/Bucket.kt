package com.zaryabshakir.mediagallery.data.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bucket(
    val id: Long,
    val name: String,
    val thumbnailUri: Uri,
    val dateModified: Long,
    val isVideo:Boolean
) : Parcelable