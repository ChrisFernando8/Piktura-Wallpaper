package com.creative.piktura

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.layoutManager = GridLayoutManager(this, 2)

        RetrofitClient.api.getWallpapers().enqueue(object :
            Callback<WallpaperResponse> {

            override fun onResponse(
                call: Call<WallpaperResponse>,
                response: Response<WallpaperResponse>
            ) {
                val wallpapers = response.body()?.wallpapers ?: emptyList()
                recycler.adapter = WallpaperAdapter(this@MainActivity, wallpapers)
            }

            override fun onFailure(call: Call<WallpaperResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity,
                    "Erro ao carregar wallpapers",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }
}
