package com.creative.piktura.ui

import android.app.WallpaperManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.creative.piktura.R
import com.creative.piktura.util.WallpaperApplier
import kotlinx.android.synthetic.main.activity_preview.*

class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        val url = intent.getStringExtra("url") ?: return

        Glide.with(this)
            .load(url)
            .into(wallpaperImage)

        btnHome.setOnClickListener {
            apply(url, WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 24) {
                apply(url, WallpaperManager.FLAG_LOCK)
            }
        }

        btnBoth.setOnClickListener {
            apply(
                url,
                WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
            )
        }
    }

    private fun apply(url: String, flag: Int) {
        WallpaperApplier.applyFromUrl(
            this,
            url,
            flag,
            onSuccess = {
                runOnUiThread {
                    Toast.makeText(this, "Wallpaper aplicado!", Toast.LENGTH_SHORT).show()
                }
            },
            onError = {
                runOnUiThread {
                    Toast.makeText(this, "Erro ao aplicar", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}
