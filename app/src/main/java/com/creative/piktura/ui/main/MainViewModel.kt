package com.creative.piktura.ui.main

import androidx.lifecycle.*
import com.creative.piktura.data.model.Wallpaper
import com.creative.piktura.data.repository.WallpaperRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = WallpaperRepository()

    private val _wallpapers = MutableLiveData<List<Wallpaper>>()
    val wallpapers: LiveData<List<Wallpaper>> = _wallpapers

    fun loadWallpapers() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchWallpapers()
            _wallpapers.postValue(result)
        }
    }
}
