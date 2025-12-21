package com.creative.piktura

import android.app.WallpaperManager
import android.content.res.Resources
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

        val wallpaperImageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        // Recebe o recurso da imagem enviado pelo adapter
        val wallpaperRes = intent.getIntExtra("wallpaperRes", -1)
        if (wallpaperRes != -1) {
            wallpaperImageView.setImageResource(wallpaperRes)
        }

        // Função para aplicar wallpaper em background
        fun setWallpaper(target: String) {
            Thread {
                try {
                    val wallpaperManager = WallpaperManager.getInstance(this)

                    // Reduz o bitmap carregado para tamanho da tela
                    val metrics = Resources.getSystem().displayMetrics

                    // Primeiro apenas lê dimensões
                    val optionsBounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                    BitmapFactory.decodeResource(resources, wallpaperRes, optionsBounds)

                    var inSampleSize = 1
                    val halfWidth = optionsBounds.outWidth / 2
                    val halfHeight = optionsBounds.outHeight / 2
                    while ((halfWidth / inSampleSize) >= metrics.widthPixels &&
                           (halfHeight / inSampleSize) >= metrics.heightPixels) {
                        inSampleSize *= 2
                    }

                    val decodeOptions = BitmapFactory.Options().apply { this.inSampleSize = inSampleSize }
                    val bitmap = BitmapFactory.decodeResource(resources, wallpaperRes, decodeOptions)

                    // Redimensiona para o tamanho exato da tela
                    val scaledBitmap = android.graphics.Bitmap.createScaledBitmap(
                        bitmap,
                        metrics.widthPixels,
                        metrics.heightPixels,
                        true
                    )

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        when (target) {
                            "home" -> wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                            "lock" -> wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_LOCK)
                            "both" -> wallpaperManager.setBitmap(
                                scaledBitmap,
                                null,
                                true,
                                WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                            )
                        }
                    } else {
                        wallpaperManager.setBitmap(scaledBitmap)
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

        // Botões chamando a função
        btnHome.setOnClickListener { setWallpaper("home") }
        btnLock.setOnClickListener { setWallpaper("lock") }
        btnBoth.setOnClickListener { setWallpaper("both") }
    }
}
