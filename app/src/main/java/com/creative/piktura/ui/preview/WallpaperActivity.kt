package com.creative.piktura.ui.preview

import android.app.WallpaperManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.creative.piktura.R
import com.creative.piktura.util.WallpaperApplier

class WallpaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val imageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        val imageUrl = intent.getStringExtra("imageUrl")

        if (imageUrl == null) {
            Toast.makeText(this, "Imagem inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Carrega a imagem normalmente com Glide
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        btnHome.setOnClickListener {
            applyWallpaper(imageUrl, WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                applyWallpaper(imageUrl, WallpaperManager.FLAG_LOCK)
            } else {
                Toast.makeText(this, "Lock screen não suportado", Toast.LENGTH_SHORT).show()
            }
        }

        btnBoth.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                applyWallpaper(
                    imageUrl,
                    WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                )
            } else {
                applyWallpaper(imageUrl, WallpaperManager.FLAG_SYSTEM)
            }
        }
    }

    private fun applyWallpaper(url: String, flag: Int) {
        WallpaperApplier.applyFromUrl(
            context = this,
            imageUrl = url,
            flag = flag
        )
    }
}
