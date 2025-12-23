package com.creative.piktura.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creative.piktura.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val wallpapers = listOf(
            "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/master/Wallpapers/wp1.jpg",
            "https://raw.githubusercontent.com/ChrisFernando8/imagens-piktura/master/Wallpapers/wp2.jpg",
            "https://raw.githubusercontent.com/ChrisFernando8/imagens-piktura/master/Wallpapers/wp3.jpg"
        )

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = WallpaperAdapter(wallpapers)
    }
}
