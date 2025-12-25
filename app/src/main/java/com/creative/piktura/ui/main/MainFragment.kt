package com.creative.piktura.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.creative.piktura.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = WallpaperAdapter { wallpaper ->
            val action =
                MainFragmentDirections.actionMainToPreview(wallpaper.image_url)
            findNavController().navigate(action)
        }

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        viewModel.wallpapers.observe(viewLifecycleOwner) {
            adapter.submit(it)
        }

        viewModel.loadWallpapers()
    }
}
