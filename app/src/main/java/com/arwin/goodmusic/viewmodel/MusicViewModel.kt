package com.arwin.goodmusic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arwin.goodmusic.model.DataSong
import com.arwin.goodmusic.repository.MusicRepository
import kotlinx.coroutines.launch

class MusicViewModel(private val repository: MusicRepository) : ViewModel() {

    private val _song = MutableLiveData<List<DataSong>?>()
    val song: MutableLiveData<List<DataSong>?> get() = _song

    private val _allSong = MutableLiveData<List<DataSong>>()
    private var allSong: List<DataSong> = emptyList()


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchMusicList() {
        viewModelScope.launch {
            try {
                val response = repository.getMusicList()
                if (response.isSuccessful) {
                    allSong = response.body() ?: emptyList()
                    _song.value = response.body()
                } else {
                    _error.value = "Failed to fetch albums"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    fun filterAlbums(query: String) {
        val filteredList = allSong.filter {
            it.name?.contains(query, ignoreCase = true) == true
        }
        _song.value = filteredList
    }

    fun restoreAllAlbums() {
        _song.value = allSong
    }
}