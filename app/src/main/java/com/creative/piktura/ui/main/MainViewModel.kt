package com.creative.piktura.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creative.piktura.data.model.Wallpaper
import com.creative.piktura.data.repository.WallpaperRepository

class MainViewModel : ViewModel() {

    private val repository = WallpaperRepository()

    private val _wallpapers = MutableLiveData<List<Wallpaper>>()
    val wallpapers: LiveData<List<Wallpaper>> = _wallpapers

    fun loadWallpapers() {
        _wallpapers.value = repository.getWallpapers()
    }
}
