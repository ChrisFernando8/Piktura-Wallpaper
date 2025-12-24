package com.creative.piktura.data.repository

import android.os.Handler
import android.os.Looper
import com.creative.piktura.data.model.Wallpaper
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

object WallpaperRepository {

    // 🔹 URL ÚNICA que puxa TODAS as imagens
    private const val DATA_URL =
        "https://466f22d3.piktura-pages.pages.dev/wallpapers.json"

    fun fetch(callback: (List<Wallpaper>) -> Unit) {
        thread {
            try {
                // Baixa o JSON
                val json = URL(DATA_URL).readText()

                // Converte para objeto
                val obj = JSONObject(json)
                val array = obj.getJSONArray("wallpapers")

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

                // Retorna na thread principal
                Handler(Looper.getMainLooper()).post {
                    callback(list)
                }

            } catch (e: Exception) {
                e.printStackTrace()

                // Retorna lista vazia em caso de erro (evita crash)
                Handler(Looper.getMainLooper()).post {
                    callback(emptyList())
                }
            }
        }
    }
}
