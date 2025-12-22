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
import java.io.IOException

class WallpaperActivity : AppCompatActivity() {

    private var loadedBitmap: Bitmap? = null
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val imageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        imageUrl = intent.getStringExtra("imageUrl") ?: return

        // 🔥 Carrega UMA VEZ e guarda o Bitmap
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    bitmap: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    loadedBitmap = bitmap
                    imageView.setImageBitmap(bitmap)
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {}
            })

        btnHome.setOnClickListener {
            applyWallpaper(WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            applyWallpaper(WallpaperManager.FLAG_LOCK)
        }

        btnBoth.setOnClickListener {
            applyWallpaper(
                WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
            )
        }
    }

    private fun applyWallpaper(flag: Int) {
        val bitmap = loadedBitmap ?: run {
            Toast.makeText(this, "Imagem ainda carregando", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val manager = WallpaperManager.getInstance(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.setBitmap(bitmap, null, true, flag)
            } else {
                manager.setBitmap(bitmap)
            }
            Toast.makeText(this, "Wallpaper aplicado!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, "Erro ao aplicar wallpaper", Toast.LENGTH_SHORT).show()
        }
    }
}
