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
) {
    companion object {
        fun getMockAlbums() = listOf(
            Album(
                id = 1,
                title = "Tales of Ithiria",
                artist = "Haggard",
                imageUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/21/72/70/21727038-f80e-0d12-140a-5c3b1a20a454/4001617424925.jpg/400x400bb.jpg",
                description = "Un álbum sinfónico que mezcla elementos de música clásica con death metal melódico."
            ),
            Album(
                id = 2,
                title = "Awake",
                artist = "Avenged Sevenfold",
                imageUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/05/92/7d/05927d2c-5b2b-4b2e-9d2b-5b2b4b2e9d2b/05927d2c-5b2b-4b2e-9d2b-5b2b4b2e9d2b.jpg/400x400bb.jpg",
                description = "Álbum clásico de metal con riffs potentes y melodías memorables."
            ),
            Album(
                id = 3,
                title = "Nightmare",
                artist = "Avenged Sevenfold",
                imageUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/6e/8b/6e/6e8b6e8b-6e8b-6e8b-6e8b-6e8b6e8b6e8b/6e8b6e8b-6e8b-6e8b-6e8b-6e8b6e8b6e8b.jpg/400x400bb.jpg",
                description = "Uno de los álbumes más icónicos del género."
            ),
            Album(
                id = 4,
                title = "Abbey Road",
                artist = "The Beatles",
                imageUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/3d/8c/8e/3d8c8e3d-8c8e-3d8c-8e3d-8c8e3d8c8e3d/3d8c8e3d-8c8e-3d8c-8e3d-8c8e3d8c8e3d.jpg/400x400bb.jpg",
                description = "El legendario álbum de los Beatles con su icónica portada."
            )
        )
    }
}
