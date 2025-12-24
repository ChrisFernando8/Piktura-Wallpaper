package com.creative.piktura.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creative.piktura.R
import com.creative.piktura.WallpaperActivity
import com.creative.piktura.data.model.Wallpaper

class WallpaperAdapter(
    private val context: Context,
    private val images: List<Wallpaper>
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgWallpaper: ImageView = view.findViewById(R.id.imgWallpaper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallpaper = images[position]

        Glide.with(context)
            .load(wallpaper.image_url)
            .centerCrop()
            .into(holder.imgWallpaper)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, WallpaperActivity::class.java)
            intent.putExtra("image_url", wallpaper.image_url)
            context.startActivity(intent)
        }
    }
}
