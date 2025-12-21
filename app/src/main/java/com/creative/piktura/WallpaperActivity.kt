package com.creative.piktura

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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

        val imageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        val imageUrl = intent.getStringExtra("wallpaperUrl") ?: return

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        fun setWallpaper(flag: Int?) {
            Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        try {
                            val manager = WallpaperManager.getInstance(this@WallpaperActivity)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && flag != null) {
                                manager.setBitmap(resource, null, true, flag)
                            } else {
                                manager.setBitmap(resource)
                            }
                            Toast.makeText(this@WallpaperActivity, "Wallpaper aplicado!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(this@WallpaperActivity, "Erro ao aplicar", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {}
                })
        }

        btnHome.setOnClickListener { setWallpaper(WallpaperManager.FLAG_SYSTEM) }
        btnLock.setOnClickListener { setWallpaper(WallpaperManager.FLAG_LOCK) }
        btnBoth.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setWallpaper(WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
            } else {
                setWallpaper(null)
            }
        }
    }
}
