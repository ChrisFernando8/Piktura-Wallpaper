package com.creative.piktura.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.creative.piktura.databinding.ActivityMainBinding
import com.creative.piktura.ui.preview.WallpaperActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = WallpaperAdapter {
            val intent = Intent(this, WallpaperActivity::class.java)
            intent.putExtra("image_url", it)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter

        viewModel.wallpapers.observe(this) {
            adapter.submitList(it)
        }

        viewModel.loadWallpapers()
    }
}
