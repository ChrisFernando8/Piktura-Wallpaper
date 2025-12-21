package com.creative.piktura

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)

        val wallpapers = listOf(
            R.drawable.wp1,
            R.drawable.wp2,
            R.drawable.wp3
        )

        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = WallpaperAdapter(this, wallpapers)
    }
}
