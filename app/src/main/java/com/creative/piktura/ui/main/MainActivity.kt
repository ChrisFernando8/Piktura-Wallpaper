package com.creative.piktura.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creative.piktura.R
import com.creative.piktura.data.WallpaperRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)

        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = WallpaperAdapter(
            WallpaperRepository.getWallpapers()
        )
    }
}
