package com.creative.piktura.util

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build

object WallpaperApplier {

    fun apply(context: Context, bitmap: Bitmap, target: Int) {
        val manager = WallpaperManager.getInstance(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            manager.setBitmap(bitmap, null, true, target)
        } else {
            manager.setBitmap(bitmap)
        }
    }
}
