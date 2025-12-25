package com.creative.piktura.ui.preview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.load
import com.creative.piktura.R
import com.creative.piktura.databinding.FragmentPreviewBinding

class PreviewFragment : Fragment(R.layout.fragment_preview) {

    private lateinit var binding: FragmentPreviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPreviewBinding.bind(view)

        val url = arguments?.getString("url") ?: return

        binding.imgPreview.load(url)
    }
}
