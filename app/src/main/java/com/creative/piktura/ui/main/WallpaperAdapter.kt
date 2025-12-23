package com.creative.piktura.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creative.piktura.R
import com.creative.piktura.domain.model.Wallpaper

class WallpaperAdapter(
    private val wallpapers: List<Wallpaper>,
    private val onClick: (Wallpaper) -> Unit
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.wallpaperImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallpaper = wallpapers[position]

        Glide.with(holder.itemView)
            .load(wallpaper.url)
            .centerCrop()
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onClick(wallpaper)
        }
    }

    override fun getItemCount(): Int = wallpapers.size
}
