package com.example.lgonzalezmusicapp.data.api

import com.example.lgonzalezmusicapp.data.model.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbumDetail(@Path("id") id: String): Album

    companion object {
        const val BASE_URL = "https://musicapi.pjasoft.com/api/"
    }
}