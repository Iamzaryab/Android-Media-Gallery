package com.zaryabshakir.mediagallery.uimodel

import android.net.Uri
import android.os.Parcelable
import com.zaryabshakir.mediagallery.data.models.Media
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaUIDataModel(private val media: Media) : MediaUIModel, Parcelable {
    override fun getId(): Long = media.id

    override fun getTitle(): String = media.title

    override fun getDisplayName(): String = media.displayName

    override fun getMimeType(): String = media.mimeType

    override fun getDateModified(): Long = media.dateModified

    override fun getUri(): Uri = media.uri

    override fun isVideo(): Boolean = media.isVideo

}