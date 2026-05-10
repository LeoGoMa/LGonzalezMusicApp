package com.example.lgonzalezmusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lgonzalezmusicapp.data.api.MusicApiService
import com.example.lgonzalezmusicapp.data.model.Album
import com.example.lgonzalezmusicapp.ui.detail.DetailScreen
import com.example.lgonzalezmusicapp.ui.home.HomeScreen
import com.example.lgonzalezmusicapp.ui.theme.LGonzalezMusicAppTheme
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Serializable
object HomeRoute

@Serializable
data class DetailRoute(val albumJson: String)

class MainActivity : ComponentActivity() {
    private val apiService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(MusicApiService.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusicApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LGonzalezMusicAppTheme {
                val navController = rememberNavController()
                var albums by remember { mutableStateOf<List<Album>>(emptyList()) }
                var isLoading by remember { mutableStateOf(true) }
                var error by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    isLoading = true
                    error = null
                    try {
                        val apiAlbums = apiService.getAlbums()
                        albums = if (apiAlbums.isNotEmpty()) apiAlbums else Album.getMockAlbums()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        // error = e.message // No mostramos el error para que use Mock directamente
                        albums = Album.getMockAlbums()
                    } finally {
                        isLoading = false
                    }
                }

                NavHost(navController = navController, startDestination = HomeRoute) {
                    composable<HomeRoute> {
                        if (isLoading) {
                            LoadingScreen()
                        } else if (error != null) {
                            ErrorScreen(message = error!!)
                        } else {
                            HomeScreen(
                                albums = albums,
                                onAlbumClick = { album ->
                                    val albumJson = kotlinx.serialization.json.Json.encodeToString(Album.serializer(), album)
                                    val encodedJson = java.net.URLEncoder.encode(albumJson, "UTF-8")
                                    navController.navigate(DetailRoute(encodedJson))
                                }
                            )
                        }
                    }
                    composable<DetailRoute> { backStackEntry ->
                        val route: DetailRoute = backStackEntry.toRoute()
                        val decodedJson = java.net.URLDecoder.decode(route.albumJson, "UTF-8")
                        val album = kotlinx.serialization.json.Json.decodeFromString(Album.serializer(), decodedJson)
                        DetailScreen(
                            album = album,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@androidx.compose.runtime.Composable
fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
    }
}