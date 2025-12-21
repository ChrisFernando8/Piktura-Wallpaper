package com.creative.piktura

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class WallpaperActivity : AppCompatActivity() {

    private lateinit var wallpaperImage: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        wallpaperImage = findViewById(R.id.wallpaperImage)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnLock = findViewById<Button>(R.id.btnLock)
        val btnBoth = findViewById<Button>(R.id.btnBoth)

        val wallpaperRes = intent.getIntExtra("wallpaperRes", -1)
        if (wallpaperRes == -1) {
            finish()
            return
        }

        wallpaperImage.setImageResource(wallpaperRes)

        // Zoom com pinça
        scaleGestureDetector = ScaleGestureDetector(this,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    scaleFactor *= detector.scaleFactor
                    scaleFactor = scaleFactor.coerceIn(1.0f, 5.0f)
                    wallpaperImage.scaleX = scaleFactor
                    wallpaperImage.scaleY = scaleFactor
                    return true
                }
            })

        btnHome.setOnClickListener { applyWallpaper(wallpaperRes, "home") }
        btnLock.setOnClickListener { applyWallpaper(wallpaperRes, "lock") }
        btnBoth.setOnClickListener { applyWallpaper(wallpaperRes, "both") }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }

    private fun applyWallpaper(resId: Int, target: String) {
        try {
            val bitmap = BitmapFactory.decodeResource(resources, resId)
            val wallpaperManager = WallpaperManager.getInstance(this)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when (target) {
                    "home" -> wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                    "lock" -> wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    "both" -> wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        true,
                        WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                    )
                }
            } else {
                wallpaperManager.setBitmap(bitmap)
            }

            Toast.makeText(this, "Wallpaper aplicado!", Toast.LENGTH_SHORT).show()

        } catch (e: IOException) {
            Toast.makeText(this, "Erro ao definir wallpaper", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
