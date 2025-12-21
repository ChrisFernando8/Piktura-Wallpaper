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
        setContentView(R.layout.activity_wallpaper)

        // Views
        val wallpaperImageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        // Recebe o recurso da imagem enviado pelo Adapter
        val wallpaperRes = intent.getIntExtra("wallpaperRes", -1)
        if (wallpaperRes != -1) {
            wallpaperImageView.setImageResource(wallpaperRes)
        }

        // Função para aplicar wallpaper em background
        fun setWallpaper(target: String) {
            Thread {
                try {
                    val wallpaperManager = WallpaperManager.getInstance(this)
                    val bitmap = BitmapFactory.decodeResource(resources, wallpaperRes)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        when (target) {
                            "home" -> wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                            "lock" -> wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                            "both" -> wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
                        }
                    } else {
                        wallpaperManager.setBitmap(bitmap)
                    }

                    runOnUiThread {
                        Toast.makeText(this, "Wallpaper applied!", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, "Error setting wallpaper", Toast.LENGTH_SHORT).show()
                    }
                    e.printStackTrace()
                }
            }.start()
        }

        // Clique dos botões
        btnHome.setOnClickListener { setWallpaper("home") }
        btnLock.setOnClickListener { setWallpaper("lock") }
        btnBoth.setOnClickListener { setWallpaper("both") }
    }
}
