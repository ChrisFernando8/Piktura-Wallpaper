package com.creative.piktura

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class WallpaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val imageView = findViewById<ImageView>(R.id.wallpaperImage)

        // Pega o recurso da imagem enviado pelo Adapter
        val wallpaperRes = intent.getIntExtra("wallpaperRes", 0)
        if (wallpaperRes != 0) {
            imageView.setImageResource(wallpaperRes)
        }
    }
}
