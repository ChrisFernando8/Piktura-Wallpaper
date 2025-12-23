package com.creative.piktura.data.repository

import com.creative.piktura.data.model.Wallpaper

class WallpaperRepository {

    fun getWallpapers(): List<Wallpaper> {
        return listOf(
            Wallpaper("1", "https://SEU_LINK_AQUI.jpg"),
            Wallpaper("2", "https://SEU_LINK_AQUI.jpg")
        )
    }
}
