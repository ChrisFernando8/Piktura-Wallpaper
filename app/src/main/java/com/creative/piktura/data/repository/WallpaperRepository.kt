package com.creative.piktura.data.repository

object WallpaperRepository {

    fun getWallpapers(): List<String> {
        return listOf(
            "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/main/Wallpapers/wp1.jpg",
            "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/main/Wallpapers/wp2.jpg",
            "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/main/Wallpapers/wp3.jpg"
        )
    }
}
