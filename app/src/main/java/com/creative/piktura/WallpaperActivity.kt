package com.creative.piktura

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream

class WallpaperActivity : AppCompatActivity() {

    private lateinit var imageUrl: String
    private lateinit var wallpaperImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        wallpaperImage = findViewById(R.id.wallpaperImage)

        imageUrl = intent.getStringExtra("url") ?: ""
        if (imageUrl.isEmpty()) {
            Toast.makeText(this, "URL inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Preview normal
        Glide.with(this)
            .load(imageUrl)
            .into(wallpaperImage)

        // Clique na imagem abre o picker nativo
        wallpaperImage.setOnClickListener {
            openSystemWallpaperPicker()
        }
    }

    private fun openSystemWallpaperPicker() {
        Glide.with(this)
            .asFile()
            .load(imageUrl)
            .into(object : CustomTarget<File>() {
                override fun onResourceReady(
                    resource: File,
                    transition: Transition<in File>?
                ) {
                    val uri: Uri = FileProvider.getUriForFile(
                        this@WallpaperActivity,
                        "$packageName.provider",
                        resource
                    )

                    val intent = Intent(Intent.ACTION_ATTACH_DATA).apply {
                        setDataAndType(uri, "image/*")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        putExtra("mimeType", "image/*")
                    }

                    startActivity(Intent.createChooser(intent, "Set Wallpaper"))
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {}
            })
    }
}
