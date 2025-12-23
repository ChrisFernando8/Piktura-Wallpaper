package com.creative.piktura.data

import com.creative.piktura.domain.model.Wallpaper

object WallpaperRepository {

    fun getWallpapers(): List<Wallpaper> {
        return listOf(
            Wallpaper(
                id = "1",
                imageUrl = "https://raw.githubusercontent.com/SEU_USUARIO/SEU_REPO/main/images/wall1.jpg"
            ),
            Wallpaper(
                id = "2",
                imageUrl = "https://raw.githubusercontent.com/SEU_USUARIO/SEU_REPO/main/images/wall2.jpg"
            )
        )
    }
}
