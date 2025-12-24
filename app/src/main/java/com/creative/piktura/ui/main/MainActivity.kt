package com.creative.piktura.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creative.piktura.R
import com.creative.piktura.data.model.Wallpaper
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        fetchWallpapers { wallpapers ->
            runOnUiThread {
                recyclerView.adapter = WallpaperAdapter(
                    context = this,
                    images = wallpapers
                )
            }
        }
    }

    private fun fetchWallpapers(callback: (List<Wallpaper>) -> Unit) {
        thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://466f22d3.piktura-pages.pages.dev/wallpapers.json")
                .build()

            val response = client.newCall(request).execute()
            val json = response.body?.string() ?: "[]"

            val array = JSONArray(json)
            val list = mutableListOf<Wallpaper>()

            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    Wallpaper(
                        id = obj.getInt("id"),
                        title = obj.getString("title"),
                        image_url = obj.getString("image_url")
                    )
                )
            }

            callback(list)
        }
    }
}
