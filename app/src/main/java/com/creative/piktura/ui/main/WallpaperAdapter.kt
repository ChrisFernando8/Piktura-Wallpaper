package com.creative.piktura.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creative.piktura.R
import com.creative.piktura.ui.preview.WallpaperActivity

class WallpaperAdapter(
    private val wallpapers: List<Wallpaper>
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wallpapers[position]

        Glide.with(holder.image.context)
            .load(item.image_url)
            .centerCrop()
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, WallpaperActivity::class.java)
            intent.putExtra("image_url", item.image_url)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = wallpapers.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgWallpaper)
    }
}
