package com.zaryabshakir.mediagallery.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zaryabshakir.mediagallery.GlideApp
import com.zaryabshakir.mediagallery.R

fun checkAndRequestGalleryPermission(activity: Activity): Boolean {
    return if (Build.VERSION.SDK_INT >= 33) {
        ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_MEDIA_VIDEO
        ) == PackageManager.PERMISSION_GRANTED

    } else {
        ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    }

}

fun loadThumbnail(imageView: ImageView, uri: Uri) {
    GlideApp.with(imageView)
        .load(uri)
        .override(300)
        .placeholder(R.drawable.default_img)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

fun load(imageView: ImageView, uri: Uri) {
    GlideApp.with(imageView)
        .load(uri)
        .override(1000)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

