package com.creative.piktura.util

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

object WallpaperApplier {

    fun applyFromUrl(
        context: Context,
        imageUrl: String,
        flag: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        Thread {
            try {
                val stream = URL(imageUrl).openStream()
                val bitmap = BitmapFactory.decodeStream(stream)

                val manager = WallpaperManager.getInstance(context)
                manager.setBitmap(bitmap, null, true, flag)

                onSuccess()
            } catch (e: Exception) {
                onError()
            }
        }.start()
    }
}
