package com.creative.piktura

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // ✅ URLs das imagens (GitHub RAW, Cloudinary, etc.)
        val wallpapers = listOf(
            "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/main/Wallpapers/wp1.jpg",
            "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/main/Wallpapers/wp2.jpg",
            "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/main/Wallpapers/wp3.jpg"
        )

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = WallpaperAdapter(wallpapers)
    }
}
