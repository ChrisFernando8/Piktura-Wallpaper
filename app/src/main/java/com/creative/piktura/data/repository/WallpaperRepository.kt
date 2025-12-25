package com.creative.piktura.data.repository

import com.creative.piktura.data.model.Wallpaper

class WallpaperRepository {

    fun getWallpapers(): List<Wallpaper> {
        return listOf(
            Wallpaper("https://SEU_SITE/pages/wp1.jpg"),
            Wallpaper("https://SEU_SITE/pages/wp2.jpg"),
            Wallpaper("https://SEU_SITE/pages/wp3.jpg")
        )
    }
}
