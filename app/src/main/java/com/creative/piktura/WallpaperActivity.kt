package com.creative.piktura

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WallpaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_wallpaper)

        val imageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        val wallpaperRes = intent.getIntExtra("wallpaperRes", 0)
        if (wallpaperRes != 0) {
            imageView.setImageResource(wallpaperRes)
        }

        val wallpaperManager = WallpaperManager.getInstance(this)

        btnHome.setOnClickListener {
            setWallpaper(wallpaperRes, WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setWallpaper(wallpaperRes, WallpaperManager.FLAG_LOCK)
            } else {
                Toast.makeText(this, "Lock screen wallpaper requires Android N or higher", Toast.LENGTH_SHORT).show()
            }
        }

        btnBoth.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setWallpaper(wallpaperRes, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
            } else {
                Toast.makeText(this, "Both wallpapers require Android N or higher", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setWallpaper(resId: Int, flag: Int) {
        try {
            val bitmap = BitmapFactory.decodeResource(resources, resId)
            val wallpaperManager = WallpaperManager.getInstance(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, flag)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }
            Toast.makeText(this, "Wallpaper applied!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error setting wallpaper", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
