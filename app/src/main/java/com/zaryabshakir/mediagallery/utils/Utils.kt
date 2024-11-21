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

fun checkMultiplePermissions(activity: Activity): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
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

fun checkPermission(activity: Activity, permission: String) = ActivityCompat.checkSelfPermission(
    activity,
    permission ) == PackageManager.PERMISSION_GRANTED

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

