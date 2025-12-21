package com.creative.piktura

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.res.Resources
import java.io.IOException

class WallpaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val wallpaperImageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        // Recebe o recurso da imagem enviado pelo adapter
        val wallpaperRes = intent.getIntExtra("wallpaperRes", -1)
        if (wallpaperRes != -1) {
            wallpaperImageView.setImageResource(wallpaperRes)
        }

        // Função para aplicar wallpaper mantendo proporção
        fun setWallpaper(target: String) {
            val wallpaperManager = WallpaperManager.getInstance(this)

            val bitmap = BitmapFactory.decodeResource(resources, wallpaperRes)

            // Redimensiona proporcionalmente para caber na tela
            val metrics = Resources.getSystem().displayMetrics
            val scale = Math.min(
                metrics.widthPixels.toFloat() / bitmap.width,
                metrics.heightPixels.toFloat() / bitmap.height
            )
            val scaledWidth = (bitmap.width * scale).toInt()
            val scaledHeight = (bitmap.height * scale).toInt()
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    when (target) {
                        "home" -> wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                        "lock" -> wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_LOCK)
                        "both" -> wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
                    }
                } else {
                    // Android < N não suporta lock screen, aplica na home
                    wallpaperManager.setBitmap(scaledBitmap)
                }
                Toast.makeText(this, "Wallpaper applied!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this, "Error setting wallpaper", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        // Botões chamando a função
        btnHome.setOnClickListener { setWallpaper("home") }
        btnLock.setOnClickListener { setWallpaper("lock") }
        btnBoth.setOnClickListener { setWallpaper("both") }
    }
}
