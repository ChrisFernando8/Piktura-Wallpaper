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
    private val wallpapers: List<String>
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = wallpapers[position]

        // 🔹 Carrega imagem no GRID
        Glide.with(holder.image.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.placeholder) // opcional
            .error(R.drawable.placeholder_error) // opcional
            .into(holder.image)

        // 🔹 Abre tela de preview
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, WallpaperActivity::class.java)
            intent.putExtra("image_url", imageUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = wallpapers.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgWallpaper)
    }
}
