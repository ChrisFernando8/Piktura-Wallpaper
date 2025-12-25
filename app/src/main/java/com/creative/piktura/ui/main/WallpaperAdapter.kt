package com.creative.piktura.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.creative.piktura.databinding.ItemWallpaperBinding
import com.creative.piktura.data.model.Wallpaper

class WallpaperAdapter(
    private val items: List<Wallpaper>,
    private val onClick: (Wallpaper) -> Unit
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemWallpaperBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWallpaperBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.imgWallpaper.load(item.url)

        holder.binding.root.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount() = items.size
}
