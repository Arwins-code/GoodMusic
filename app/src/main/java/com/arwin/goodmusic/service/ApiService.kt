package com.arwin.goodmusic.service

import com.arwin.goodmusic.model.Album
import com.arwin.goodmusic.model.DataSong
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    suspend fun getAlbums(@Query("s") artistName: String): Response<List<Album>>

    @GET("adrien?format=json")
    suspend fun getMusicList(): Response<List<DataSong>>
}