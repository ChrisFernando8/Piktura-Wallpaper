package com.creative.piktura.ui.preview

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

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

        imageUrl = intent.getStringExtra("image_url") ?: ""

        if (imageUrl.isEmpty()) {
            Toast.makeText(this, "Imagem inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Preview seguro
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        btnHome.setOnClickListener { applyWallpaper(WallpaperManager.FLAG_SYSTEM) }
        btnLock.setOnClickListener { applyWallpaper(WallpaperManager.FLAG_LOCK) }
        btnBoth.setOnClickListener {
            applyWallpaper(WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
        }
    }

    private fun applyWallpaper(flag: Int) {

        val metrics: DisplayMetrics = resources.displayMetrics
        val targetWidth = metrics.widthPixels
        val targetHeight = metrics.heightPixels

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .override(targetWidth, targetHeight) // 🔥 EVITA OOM
            .centerCrop()
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    bitmap: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    try {
                        val manager = WallpaperManager.getInstance(this@WallpaperActivity)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
                        e.printStackTrace()
                    }
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {}
            })
    }
}
