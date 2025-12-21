package com.creative.piktura

import android.app.WallpaperManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class WallpaperActivity : AppCompatActivity() {

    private lateinit var wallpaperImageView: ImageView
    private lateinit var btnHome: Button
    private lateinit var btnLock: Button
    private lateinit var btnBoth: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        wallpaperImageView = findViewById(R.id.wallpaperImage)
        btnHome = findViewById(R.id.btnSetHome)
        btnLock = findViewById(R.id.btnSetLock)
        btnBoth = findViewById(R.id.btnSetBoth)

        // Recebe o recurso da imagem enviado pelo adapter
        val wallpaperRes = intent.getIntExtra("wallpaperRes", -1)
        if (wallpaperRes != -1) {
            val bitmap = BitmapFactory.decodeResource(resources, wallpaperRes)
            wallpaperImageView.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "Erro ao carregar a imagem", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Função para aplicar wallpaper
        fun setWallpaper(target: String) {
            val wallpaperManager = WallpaperManager.getInstance(this)

            val metrics = Resources.getSystem().displayMetrics
            val bitmap = BitmapFactory.decodeResource(resources, wallpaperRes)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, metrics.widthPixels, metrics.heightPixels, true)

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    when (target) {
                        "home" -> wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                        "lock" -> {
                            try {
                                wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_LOCK)
                            } catch (e: Exception) {
                                Toast.makeText(this, "Não foi possível aplicar na tela de bloqueio", Toast.LENGTH_SHORT).show()
                                return
                            }
                        }
                        "both" -> {
                            try {
                                wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
                            } catch (e: Exception) {
                                wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                                Toast.makeText(this, "Aplicado somente na tela inicial (lock screen não permitido)", Toast.LENGTH_SHORT).show()
                                return
                            }
                        }
                    }
                } else {
                    wallpaperManager.setBitmap(scaledBitmap)
                    Toast.makeText(this, "Aplicado na tela inicial!", Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(this, "Wallpaper aplicado!", Toast.LENGTH_SHORT).show()

            } catch (e: IOException) {
                Toast.makeText(this, "Erro ao definir wallpaper", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        // Botões chamando a função
        btnHome.setOnClickListener { setWallpaper("home") }
        btnLock.setOnClickListener { setWallpaper("lock") }
        btnBoth.setOnClickListener { setWallpaper("both") }
    }
}
