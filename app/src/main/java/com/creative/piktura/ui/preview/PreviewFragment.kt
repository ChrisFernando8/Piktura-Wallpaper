package com.creative.piktura.ui.preview

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.creative.piktura.databinding.FragmentPreviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreviewFragment : Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    private val args: PreviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Preview da imagem
        binding.imgPreview.load(args.imageUrl)

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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val loader = ImageLoader(requireContext())
                val request = ImageRequest.Builder(requireContext())
                    .data(args.imageUrl)
                    .allowHardware(false) // 🔥 evita crash
                    .build()

                val result = loader.execute(request)
                val bitmap = (result as SuccessResult).drawable
                    .toBitmap()
                    .copy(Bitmap.Config.ARGB_8888, false)

                val manager = WallpaperManager.getInstance(requireContext())

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    manager.setBitmap(bitmap, null, true, flag)
                } else {
                    manager.setBitmap(bitmap)
                }

                launch(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Wallpaper aplicado!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao aplicar wallpaper",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
