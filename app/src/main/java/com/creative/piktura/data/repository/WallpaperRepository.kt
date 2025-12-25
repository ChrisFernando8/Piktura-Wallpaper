package com.creative.piktura.data.repository

import com.creative.piktura.data.model.Wallpaper
import org.json.JSONObject
import java.net.URL

class WallpaperRepository {

    private val DATA_URL =
        "https://SEU_DOMINIO.pages.dev/wallpapers.json"

    fun fetchWallpapers(): List<Wallpaper> {
        val json = URL(DATA_URL).readText()
        val array = JSONObject(json).getJSONArray("wallpapers")

        val list = mutableListOf<Wallpaper>()
        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            list.add(
                Wallpaper(
                    id = item.getInt("id"),
                    title = item.getString("title"),
                    image_url = item.getString("image_url")
                )
            )
        }
        return list
    }
}
