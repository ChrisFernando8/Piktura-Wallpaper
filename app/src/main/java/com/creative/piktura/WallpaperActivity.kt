package com.creative.piktura

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class WallpaperActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_WALLPAPER_URL = "WALLPAPER_URL"
    }

    private lateinit var wallpaperImage: ImageView
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        wallpaperImage = findViewById(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        imageUrl = intent.getStringExtra(EXTRA_WALLPAPER_URL) ?: ""

        if (imageUrl.isEmpty()) {
            Toast.makeText(this, "URL inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Preview fullscreen (igual app de referência)
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(wallpaperImage)

        btnHome.setOnClickListener {
            setWallpaper(WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            setWallpaper(WallpaperManager.FLAG_LOCK)
        }

        btnBoth.setOnClickListener {
            setWallpaper(
                WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
            )
        }
    }

    private fun setWallpaper(flag: Int) {
        val manager = WallpaperManager.getInstance(this)

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            manager.setBitmap(resource, null, true, flag)
                        } else {
                            manager.setBitmap(resource)
                        }
                        Toast.makeText(
                            this@WallpaperActivity,
                            "Wallpaper aplicado!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@WallpaperActivity,
                            "Erro ao aplicar wallpaper",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}
