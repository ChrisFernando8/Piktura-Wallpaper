package com.creative.piktura.ui.preview

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.creative.piktura.R

class WallpaperActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnHome: Button
    private lateinit var btnLock: Button
    private lateinit var btnBoth: Button

    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        imageView = findViewById(R.id.wallpaperImage)
        btnHome = findViewById(R.id.btnHome)
        btnLock = findViewById(R.id.btnLock)
        btnBoth = findViewById(R.id.btnBoth)

        // 🔒 URL obrigatória
        val url = intent.getStringExtra("image_url")

        if (url.isNullOrBlank() || !url.startsWith("http")) {
            Toast.makeText(this, "Imagem inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        imageUrl = url

        // 🔹 Preview seguro
        Glide.with(this)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)

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

        Toast.makeText(this, "Aplicando wallpaper...", Toast.LENGTH_SHORT).show()

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    try {
                        val manager = WallpaperManager.getInstance(this@WallpaperActivity)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            manager.setBitmap(resource, null, true, flag)
                        } else {
                            manager.setBitmap(resource)
                        }

                        Toast.makeText(
                            this@WallpaperActivity,
                            "Wallpaper aplicado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } catch (e: Exception) {
                        Log.e("Wallpaper", "Erro ao aplicar", e)
                        Toast.makeText(
                            this@WallpaperActivity,
                            "Erro ao aplicar wallpaper",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {
                    // Obrigatório — evita crash
                }

                override fun onLoadFailed(errorDrawable: android.graphics.drawable.Drawable?) {
                    Toast.makeText(
                        this@WallpaperActivity,
                        "Falha ao carregar imagem",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
