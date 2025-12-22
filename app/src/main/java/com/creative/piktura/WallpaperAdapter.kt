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
    private val wallpapers: List<String>
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = wallpapers[position]

        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(holder.image)

        holder.image.setOnClickListener {
            val intent = Intent(context, WallpaperActivity::class.java)
            intent.putExtra("Imageurl", url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = wallpapers.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgWallpaper)
    }
}
