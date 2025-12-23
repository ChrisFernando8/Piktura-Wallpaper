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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val wallpaperImage = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        val imageUrl = intent.getStringExtra("image_url")

        if (imageUrl.isNullOrEmpty()) {
            Toast.makeText(this, "Imagem inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 🔹 Preview da imagem
        Glide.with(this)
            .load(imageUrl)
            .into(wallpaperImage)

        btnHome.setOnClickListener {
            setWallpaperFromUrl(imageUrl, WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            setWallpaperFromUrl(imageUrl, WallpaperManager.FLAG_LOCK)
        }

        btnBoth.setOnClickListener {
            setWallpaperFromUrl(
                imageUrl,
                WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
            )
        }
    }

    private fun setWallpaperFromUrl(url: String, flag: Int) {
        val wallpaperManager = WallpaperManager.getInstance(this)

        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    bitmap: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            wallpaperManager.setBitmap(bitmap, null, true, flag)
                        } else {
                            wallpaperManager.setBitmap(bitmap)
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
                        e.printStackTrace()
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}
