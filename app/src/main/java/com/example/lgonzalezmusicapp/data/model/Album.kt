package com.example.lgonzalezmusicapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: Int,
    val title: String,
    val artist: String,
    val imageUrl: String,
    val description: String? = null,
    val releaseDate: String? = null,
    val genre: String? = null
)
