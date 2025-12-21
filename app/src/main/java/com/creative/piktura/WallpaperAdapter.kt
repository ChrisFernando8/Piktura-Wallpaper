package com.creative.piktura

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WallpaperAdapter(
    private val context: Context,
    private val wallpapers: List<WallpaperItem>
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgWallpaper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wallpapers[position]

        Glide.with(context)
            .load(item.url)
            .centerCrop()
            .into(holder.image)

        holder.image.setOnClickListener {
            val intent = Intent(context, WallpaperActivity::class.java)
            intent.putExtra("imageUrl", item.url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = wallpapers.size
}
