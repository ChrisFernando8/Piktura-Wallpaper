package com.creative.piktura.data.repository

import com.creative.piktura.data.model.Wallpaper

class WallpaperRepository {

    fun getWallpapers(): List<Wallpaper> {
        return listOf(
            Wallpaper("https://SEU_SITE/wp1.jpg"),
            Wallpaper("https://SEU_SITE/wp2.jpg"),
            Wallpaper("https://SEU_SITE/wp3.jpg")
        )
    }
}
