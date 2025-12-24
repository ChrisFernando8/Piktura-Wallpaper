package com.creative.piktura.ui.preview

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.creative.piktura.R
import kotlin.concurrent.thread

class WallpaperActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnHome: Button
    private lateinit var btnLock: Button
    private lateinit var btnBoth: Button

    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        imageView = findViewById(R.id.wallpaperImage)
        btnHome = findViewById(R.id.btnHome)
        btnLock = findViewById(R.id.btnLock)
        btnBoth = findViewById(R.id.btnBoth)

        imageUrl = intent.getStringExtra("image_url")

        if (imageUrl.isNullOrEmpty()) {
            Toast.makeText(this, "Imagem inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Preview seguro
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        btnHome.setOnClickListener { setWallpaperSafe(WallpaperManager.FLAG_SYSTEM) }
        btnLock.setOnClickListener { setWallpaperSafe(WallpaperManager.FLAG_LOCK) }
        btnBoth.setOnClickListener {
            setWallpaperSafe(
                WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
            )
        }
    }

    private fun setWallpaperSafe(flag: Int) {
        thread {
            try {
                val manager = WallpaperManager.getInstance(this)

                Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .into(object : CustomTarget<Bitmap>() {

                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            try {
                                val metrics = DisplayMetrics()
                                windowManager.defaultDisplay.getMetrics(metrics)

                                val scaledBitmap = Bitmap.createScaledBitmap(
                                    resource,
                                    metrics.widthPixels,
                                    metrics.heightPixels,
                                    true
                                )

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    manager.setBitmap(scaledBitmap, null, true, flag)
                                } else {
                                    manager.setBitmap(scaledBitmap)
                                }

                                runOnUiThread {
                                    Toast.makeText(
                                        this@WallpaperActivity,
                                        "Wallpaper aplicado!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                                runOnUiThread {
                                    Toast.makeText(
                                        this@WallpaperActivity,
                                        "Erro ao aplicar wallpaper",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                        override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {}
                    })

            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Erro inesperado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
