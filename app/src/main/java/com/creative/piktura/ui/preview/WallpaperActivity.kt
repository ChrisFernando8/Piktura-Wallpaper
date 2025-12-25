package com.creative.piktura.ui.preview

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.creative.piktura.databinding.ActivityWallpaperBinding

class WallpaperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWallpaperBinding
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUrl = intent.getStringExtra("image_url") ?: run {
            finish()
            return
        }

        Glide.with(this)
            .load(imageUrl)
            .into(binding.wallpaperImage)

        binding.btnHome.setOnClickListener {
            applyWallpaper(WallpaperManager.FLAG_SYSTEM)
        }

        binding.btnLock.setOnClickListener {
            applyWallpaper(WallpaperManager.FLAG_LOCK)
        }

        binding.btnBoth.setOnClickListener {
            applyWallpaper(
                WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
            )
        }
    }

    private fun applyWallpaper(flag: Int) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val manager = WallpaperManager.getInstance(this@WallpaperActivity)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        manager.setBitmap(resource, null, true, flag)
                    } else {
                        manager.setBitmap(resource)
                    }

                    Toast.makeText(
                        this@WallpaperActivity,
                        "Wallpaper aplicado!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {}
            })
    }
}
