package com.creative.piktura

import android.app.WallpaperManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class WallpaperAdapter(
    private val context: Context,
    private val wallpapers: List<Int>
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgWallpaper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resId = wallpapers[position]
        holder.image.setImageResource(resId)

        holder.image.setOnClickListener {
            val manager = WallpaperManager.getInstance(context)
            manager.setResource(resId)
        }
    }

    override fun getItemCount() = wallpapers.size
}
