package com.zaryabshakir.mediagallery.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

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

fun load(imageView: ImageView, uri: Uri, @DrawableRes fallback: Int = 0) {
//    GlideApp.with(imageView)
//        .load(uri)
//        .override(200)
//        .also {
//            if (fallback != 0) {
//                it.fallback(fallback)
//                    .error(fallback)
//            }
//
//        }
//        .transition(DrawableTransitionOptions.withCrossFade())
//        .into(imageView)
}
