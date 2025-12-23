package com.creative.piktura.ui.preview

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.creative.piktura.R

class WallpaperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        val imageView = findViewById<ImageView>(R.id.wallpaperImage)
        val btnApply = findViewById<Button>(R.id.btnApply)

        val imageUrl = intent.getStringExtra("wallpaperUrl") ?: return

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        btnApply.setOnClickListener {
            applyWallpaper(imageUrl)
        }
    }

    private fun applyWallpaper(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val manager = WallpaperManager.getInstance(this@WallpaperActivity)
                    manager.setBitmap(resource)
                    Toast.makeText(this@WallpaperActivity, "Wallpaper aplicado!", Toast.LENGTH_SHORT).show()
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}
