package com.creative.piktura.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creative.piktura.R
import com.creative.piktura.data.model.Wallpaper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val wallpapers = listOf(
            Wallpaper(1, "Wallpaper 1", "https://466f22d3.piktura-pages.pages.dev/wallpapers/wp1.jpg"),
            Wallpaper(2, "Wallpaper 2", "https://466f22d3.piktura-pages.pages.dev/wallpapers/wp2.jpg"),
            Wallpaper(3, "Wallpaper 3", "https://466f22d3.piktura-pages.pages.dev/wallpapers/wp3.jpg")
        )

        recyclerView.adapter = WallpaperAdapter(
            context = this,
            images = wallpapers
        )
    }
}
