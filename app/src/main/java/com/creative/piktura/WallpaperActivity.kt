package com.creative.piktura

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class WallpaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val imageRes = intent.getIntExtra("imageRes", 0)
        val imageView = findViewById<ImageView>(R.id.wallpaperImageView)
        imageView.setImageResource(imageRes)
    }
}
