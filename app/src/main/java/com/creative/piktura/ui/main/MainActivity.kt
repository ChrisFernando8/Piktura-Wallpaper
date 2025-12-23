package com.creative.piktura.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.creative.piktura.R
import com.creative.piktura.data.repository.WallpaperRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = WallpaperAdapter(
            WallpaperRepository.getWallpapers()
        )
    }
}
