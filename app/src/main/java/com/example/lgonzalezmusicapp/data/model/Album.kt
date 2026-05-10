package com.example.lgonzalezmusicapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val image: String,        // ← renombrado, coincide con la API
    val description: String? = null,
    val releaseDate: String? = null,
    val genre: String? = null
) {
    companion object {
        fun getMockAlbums() = listOf(
            Album(id = "1", title = "Tales of Ithiria", artist = "Haggard",
                image = "https://upload.wikimedia.org/wikipedia/en/3/3b/Dark_Side_of_the_Moon.png"),
            Album(id = "2", title = "Awake", artist = "Avenged Sevenfold",
                image = "https://upload.wikimedia.org/wikipedia/en/4/42/Beatles_-_Abbey_Road.jpg"),
            Album(id = "3", title = "Nightmare", artist = "Avenged Sevenfold",
                image = "https://upload.wikimedia.org/wikipedia/en/5/55/Michael_Jackson_-_Thriller.png"),
            Album(id = "4", title = "Abbey Road", artist = "The Beatles",
                image = "https://upload.wikimedia.org/wikipedia/commons/9/91/ACDC_Back_in_Black.png")
        )
    }
}