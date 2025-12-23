package com.creative.piktura.util

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WallpaperApplier {

    suspend fun applyFromUrl(
        context: Context,
        bitmap: Bitmap,
        target: Int
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val manager = WallpaperManager.getInstance(context)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.setBitmap(bitmap, null, true, target)
            } else {
                manager.setBitmap(bitmap)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
