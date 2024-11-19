package com.zaryabshakir.mediagallery.data.models

import android.net.Uri

data class Media(
    val id: Long,
    val title: String,
    val displayName: String,
    val size: Long,
    val mimeType: String,
    val dateModified: Long,
    val uri: Uri,
    val isVideo:Boolean
)