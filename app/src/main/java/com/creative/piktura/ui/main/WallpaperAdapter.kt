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

class WallpaperAdapter(
    private val context: Context,
    private val images: List<String>
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgWallpaper: ImageView = view.findViewById(R.id.imgWallpaper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = images[position]

        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(holder.imgWallpaper)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, WallpaperActivity::class.java)
            intent.putExtra("image_url", imageUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = images.size
}
