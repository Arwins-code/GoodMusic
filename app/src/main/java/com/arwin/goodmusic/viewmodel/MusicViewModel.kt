package com.arwin.goodmusic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arwin.goodmusic.model.Album
import com.arwin.goodmusic.repository.MusicRepository
import kotlinx.coroutines.launch

class MusicViewModel(private val repository: MusicRepository) : ViewModel() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchAlbums(artistName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAlbums(artistName)
                if (response.isSuccessful) {
                    _albums.value = response.body()
                } else {
                    _error.value = "Failed to fetch albums"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }
}