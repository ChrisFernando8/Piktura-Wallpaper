package com.creative.piktura.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creative.piktura.data.model.Wallpaper
import com.creative.piktura.databinding.ItemWallpaperBinding

class WallpaperAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<WallpaperAdapter.VH>() {

    private val items = mutableListOf<Wallpaper>()

    fun submitList(list: List<Wallpaper>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemWallpaperBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        Glide.with(holder.binding.root)
            .load(item.imageUrl)
            .centerCrop()
            .into(holder.binding.imgWallpaper)

        holder.binding.root.setOnClickListener {
            onClick(item.imageUrl)
        }
    }

    override fun getItemCount() = items.size

    class VH(val binding: ItemWallpaperBinding) : RecyclerView.ViewHolder(binding.root)
}
