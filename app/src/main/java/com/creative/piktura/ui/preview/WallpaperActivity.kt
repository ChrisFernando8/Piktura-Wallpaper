package com.creative.piktura.ui.preview

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
import com.creative.piktura.R

class WallpaperActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMAGE_URL = "image_url"
    }

    private lateinit var wallpaperImage: ImageView
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        // Views
        wallpaperImage = findViewById(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        // Recebe URL
        imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL) ?: ""

        if (imageUrl.isBlank()) {
            Toast.makeText(this, "Imagem inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 🔹 Preview da imagem (APENAS EXIBIÇÃO)
        Glide.with(this)
            .load(imageUrl)
            .into(wallpaperImage)

        // Botões
        btnHome.setOnClickListener {
            applyWallpaper(WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            applyWallpaper(WallpaperManager.FLAG_LOCK)
        }

        btnBoth.setOnClickListener {
            applyWallpaper(WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
        }
    }

    /**
     * 🔥 Aplica wallpaper baixando o Bitmap corretamente
     */
    private fun applyWallpaper(flag: Int) {
        val wallpaperManager = WallpaperManager.getInstance(this)

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
                            wallpaperManager.setBitmap(resource, null, true, flag)
                        } else {
                            wallpaperManager.setBitmap(resource)
                        }

                        Toast.makeText(
                            this@WallpaperActivity,
                            "Wallpaper aplicado com sucesso!",
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
