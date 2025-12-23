package com.creative.piktura.data.repository

import com.creative.piktura.domain.model.Wallpaper

object WallpaperRepository {

    fun getWallpapers(): List<Wallpaper> {
        return listOf(
            Wallpaper(
                id = "1",
                url = "https://raw.githubusercontent.com/SEU_USUARIO/SEU_REPO/main/wallpapers/w1.jpg"
            ),
            Wallpaper(
                id = "2",
                url = "https://raw.githubusercontent.com/SEU_USUARIO/SEU_REPO/main/wallpapers/w2.jpg"
            )
        )
    }
}
