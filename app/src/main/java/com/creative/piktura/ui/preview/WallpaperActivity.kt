package com.creative.piktura.ui.preview

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
import com.creative.piktura.R

class WallpaperActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnHome: Button
    private lateinit var btnLock: Button
    private lateinit var btnBoth: Button

    private var loadedBitmap: Bitmap? = null
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        imageView = findViewById(R.id.wallpaperImage)
        btnHome = findViewById(R.id.btnHome)
        btnLock = findViewById(R.id.btnLock)
        btnBoth = findViewById(R.id.btnBoth)

        // URL recebida da tela anterior
        imageUrl = intent.getStringExtra("imageUrl")

        if (imageUrl.isNullOrEmpty()) {
            Toast.makeText(this, "Imagem inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 🔹 Carrega imagem da URL como Bitmap (FORMA CERTA)
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    loadedBitmap = resource
                    imageView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {
                    loadedBitmap = null
                }
            })

        btnHome.setOnClickListener { applyWallpaper(WallpaperManager.FLAG_SYSTEM) }
        btnLock.setOnClickListener { applyWallpaper(WallpaperManager.FLAG_LOCK) }
        btnBoth.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                applyWallpaper(
                    WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                )
            } else {
                applyWallpaper(WallpaperManager.FLAG_SYSTEM)
            }
        }
    }

    private fun applyWallpaper(flag: Int) {
        val bitmap = loadedBitmap
        if (bitmap == null) {
            Toast.makeText(this, "Imagem ainda carregando...", Toast.LENGTH_SHORT).show()
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

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao aplicar wallpaper", Toast.LENGTH_SHORT).show()
        }
    }
}
