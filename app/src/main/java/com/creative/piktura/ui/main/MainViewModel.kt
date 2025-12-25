package com.creative.piktura.ui.main

import androidx.lifecycle.ViewModel
import com.creative.piktura.data.repository.WallpaperRepository

class MainViewModel : ViewModel() {

    private val repository = WallpaperRepository()

    val wallpapers = repository.getWallpapers()
}
