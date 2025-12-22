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
import java.io.IOException

class WallpaperActivity : AppCompatActivity() {

    private lateinit var wallpaperImage: ImageView
    private var loadedBitmap: Bitmap? = null
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        wallpaperImage = findViewById(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        // 🔹 Recebe a URL da imagem
        imageUrl = intent.getStringExtra("url") ?: ""

        if (imageUrl.isEmpty()) {
            Toast.makeText(this, "URL inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 🔹 Carrega a imagem UMA vez e mantém o Bitmap
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    loadedBitmap = resource
                    wallpaperImage.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        // Botões
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
        val bitmap = loadedBitmap

        if (bitmap == null) {
            Toast.makeText(this, "Imagem ainda carregando", Toast.LENGTH_SHORT).show()
            return
        }

        val wallpaperManager = WallpaperManager.getInstance(this)

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, flag)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }

            Toast.makeText(this, "Wallpaper aplicado!", Toast.LENGTH_SHORT).show()

        } catch (e: IOException) {
            Toast.makeText(this, "Erro ao definir wallpaper", Toast.LENGTH_SHORT).show()
        }
    }
}
