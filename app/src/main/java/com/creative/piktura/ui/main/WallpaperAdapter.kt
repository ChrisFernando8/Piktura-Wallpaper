package com.creative.piktura.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.creative.piktura.R
import com.creative.piktura.data.model.Wallpaper
import kotlinx.android.synthetic.main.item_wallpaper.view.*

class WallpaperAdapter(
    private val onClick: (Wallpaper) -> Unit
) : RecyclerView.Adapter<WallpaperAdapter.VH>() {

    private val items = mutableListOf<Wallpaper>()

    fun submit(list: List<Wallpaper>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p: ViewGroup, v: Int): VH =
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_wallpaper, p, false))

    override fun onBindViewHolder(h: VH, i: Int) {
        val item = items[i]
        h.itemView.imgWallpaper.load(item.image_url)
        h.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size

    class VH(v: View) : RecyclerView.ViewHolder(v)
}
