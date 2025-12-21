package com.creative.piktura

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class WallpaperAdapter(
    private val context: Context,
    private val wallpapers: List<Int>
) : RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_wallpaper, parent, false)
        return WallpaperViewHolder(view)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        val wallpaperRes = wallpapers[position]
        holder.imageView.setImageResource(wallpaperRes)

        // Clique para abrir WallpaperActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WallpaperActivity::class.java)
            intent.putExtra("wallpaperResId", wallpaperRes)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = wallpapers.size

    class WallpaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.wallpaperImage)
    }
}
