package com.creative.piktura.ui.preview

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.creative.piktura.R
import com.creative.piktura.domain.model.Wallpaper

class WallpaperActivity : AppCompatActivity() {

    private var loadedBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val image = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        val wallpaper = intent.getSerializableExtra("wallpaper") as Wallpaper

        Glide.with(this)
            .asBitmap()
            .load(wallpaper.url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    loadedBitmap = resource
                    image.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        fun apply(flag: Int) {
            val bmp = loadedBitmap ?: return
            try {
                WallpaperManager.getInstance(this)
                    .setBitmap(bmp, null, true, flag)
                Toast.makeText(this, "Wallpaper aplicado", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Erro ao aplicar wallpaper", Toast.LENGTH_SHORT).show()
            }
        }

        btnHome.setOnClickListener {
            apply(WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            apply(WallpaperManager.FLAG_LOCK)
        }

        btnBoth.setOnClickListener {
            apply(WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
        }
    }
}
