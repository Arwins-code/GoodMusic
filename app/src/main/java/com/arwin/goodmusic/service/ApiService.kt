package com.arwin.goodmusic.service

import com.arwin.goodmusic.model.Album
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("searchalbum.php")
    suspend fun getAlbums(@Query("s") artistName: String): Response<List<Album>>
}