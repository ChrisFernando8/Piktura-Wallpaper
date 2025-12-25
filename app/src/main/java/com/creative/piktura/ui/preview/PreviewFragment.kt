package com.creative.piktura.ui.preview

import android.app.WallpaperManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.creative.piktura.R
import kotlinx.android.synthetic.main.fragment_preview.*

class PreviewFragment : Fragment(R.layout.fragment_preview) {

    private val args: PreviewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        imgPreview.load(args.imageUrl)

        btnHome.setOnClickListener {
            apply(WallpaperManager.FLAG_SYSTEM)
        }

        btnLock.setOnClickListener {
            apply(WallpaperManager.FLAG_LOCK)
        }

        btnBoth.setOnClickListener {
            apply(WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
        }
    }

    private fun apply(flag: Int) {
        imgPreview.drawable?.let {
            val bitmap = (it as android.graphics.drawable.BitmapDrawable).bitmap
            val manager = WallpaperManager.getInstance(requireContext())

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.setBitmap(bitmap, null, true, flag)
            } else {
                manager.setBitmap(bitmap)
            }
        }
    }
}
