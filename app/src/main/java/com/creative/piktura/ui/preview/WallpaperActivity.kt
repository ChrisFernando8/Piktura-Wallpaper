package com.creative.piktura

import android.app.WallpaperManager
import android.graphics.Bitmap
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

    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val imageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        imageUrl = intent.getStringExtra("wallpaperUrl") ?: ""

        if (imageUrl.isEmpty()) {
            Toast.makeText(this, "Imagem inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Preview da imagem
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        btnHome.setOnClickListener { applyWallpaper(WallpaperManager.FLAG_SYSTEM) }
        btnLock.setOnClickListener { applyWallpaper(WallpaperManager.FLAG_LOCK) }
        btnBoth.setOnClickListener { applyWallpaper(-1) }
    }

    private fun applyWallpaper(flag: Int) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    bitmap: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    try {
                        val manager = WallpaperManager.getInstance(this@WallpaperActivity)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && flag != -1) {
                            manager.setBitmap(bitmap, null, true, flag)
                        } else {
                            manager.setBitmap(bitmap)
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

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {}
            })
    }
}
