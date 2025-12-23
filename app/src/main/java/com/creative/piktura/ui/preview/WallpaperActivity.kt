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

        // ✅ SEGURANÇA: valida se a URL chegou
        val url = intent.getStringExtra("image_url")
        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        imageUrl = url

        // 🔹 Preview da imagem
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)

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

    private fun applyWallpaper(flag: Int) {
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

                override fun onLoadCleared(placeholder: Drawable?) {
                    // nada aqui
                }
            })
    }
}
