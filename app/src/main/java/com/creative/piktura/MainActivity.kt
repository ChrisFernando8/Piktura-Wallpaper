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

        // 🔗 Lista de wallpapers por URL (Cloudinary)
        val wallpapers = listOf(
            "https://res.cloudinary.com/dbtturm2d/image/upload/v1766310175/wp1.jpg",
            "https://res.cloudinary.com/dbtturm2d/image/upload/v1766310175/wp2.jpg",
            "https://res.cloudinary.com/dbtturm2d/image/upload/v1766310175/wp3_p9d3xk.jpg"
        )

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = WallpaperAdapter(this, wallpapers)
    }
}
