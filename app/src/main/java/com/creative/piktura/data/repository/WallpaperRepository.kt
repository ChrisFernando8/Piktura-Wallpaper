package com.creative.piktura.data.repository

data class Wallpaper(
    val id: Int,
    val url: String
)

object WallpaperRepository {

    fun getWallpapers(): List<Wallpaper> {
        return listOf(
            Wallpaper(
                1,
                "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/main/Wallpapers/wp1.jpg"
            ),
            Wallpaper(
                2,
                "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/main/Wallpapers/wp2.jpg"
            ),
            Wallpaper(
                3,
                "https://raw.githubusercontent.com/ChrisFernando8/Imagens-piktura/main/Wallpapers/wp3.jpg"
            )
        )
    }
}
