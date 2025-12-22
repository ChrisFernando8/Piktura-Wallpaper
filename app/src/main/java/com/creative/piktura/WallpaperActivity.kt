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
import java.io.IOException

class WallpaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val imageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        val url = intent.getStringExtra("wallpaperUrl") ?: return

        // 🔥 Carrega imagem SEM distorcer
        Glide.with(this)
            .load(url)
            .fitCenter()
            .into(imageView)

        fun applyWallpaper(flag: Int?) {
            try {
                val drawable = imageView.drawable as BitmapDrawable
                val bitmap: Bitmap = drawable.bitmap
                val manager = WallpaperManager.getInstance(this)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && flag != null) {
                    manager.setBitmap(bitmap, null, true, flag)
                } else {
                    manager.setBitmap(bitmap)
                }

                Toast.makeText(this, "Wallpaper aplicado!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this, "Erro ao definir wallpaper", Toast.LENGTH_SHORT).show()
            }
        }

        btnHome.setOnClickListener {
            applyWallpaper(WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                applyWallpaper(WallpaperManager.FLAG_LOCK)
            } else {
                Toast.makeText(this, "Lock screen não suportado", Toast.LENGTH_SHORT).show()
            }
        }

        btnBoth.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                applyWallpaper(
                    WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                )
            } else {
                applyWallpaper(null)
            }
        }
    }
}
