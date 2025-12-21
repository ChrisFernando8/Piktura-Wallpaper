package com.creative.piktura

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class WallpaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper) // nome do layout XML

        val imageView = findViewById<ImageView>(R.id.wallpaperImage)

        // Recebendo o recurso da intent
        val wallpaperResId = intent.getIntExtra("wallpaperResId", 0)
        if (wallpaperResId != 0) {
            imageView.setImageResource(wallpaperResId)
        }
    }
}
