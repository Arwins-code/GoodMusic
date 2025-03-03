package com.arwin.goodmusic.repository

import com.arwin.goodmusic.model.Album
import com.arwin.goodmusic.service.ApiService
import retrofit2.Response

class MusicRepository(private val apiService: ApiService) {

    suspend fun getAlbums(artistName: String): Response<List<Album>> {
        return apiService.getAlbums(artistName)
    }
}