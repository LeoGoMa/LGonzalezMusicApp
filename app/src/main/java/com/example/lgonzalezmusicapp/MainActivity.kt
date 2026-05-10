package com.example.lgonzalezmusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Serializable
object HomeRoute

@Serializable
data class DetailRoute(val albumJson: String)

class MainActivity : ComponentActivity() {
    private val apiService by lazy {
        Retrofit.Builder()
            .baseUrl(MusicApiService.BASE_URL)
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
                    
                    // Prioritize Mock Data so you can see the app immediately
                    // The API is currently down or unreachable from the emulator
                    albums = Album.getMockAlbums()
                    isLoading = false
                    
                    // Try to fetch from API in background if you want, 
                    // but we already have the mock data ready.
                    try {
                        val apiAlbums = apiService.getAlbums()
                        if (apiAlbums.isNotEmpty()) {
                            albums = apiAlbums
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        // Keep using mock data if API fails
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
                                    // Use simple ID or full object if wanted.
                                    // For Serializable navigation with complex objects, 
                                    // we can pass a JSON or just the ID and fetch again.
                                    // Requirement says navigate passing ID or object.
                                    // Let's pass the object as a JSON string for simplicity with Serializable routes.
                                    val albumJson = kotlinx.serialization.json.Json.encodeToString(Album.serializer(), album)
                                    navController.navigate(DetailRoute(albumJson))
                                }
                            )
                        }
                    }
                    composable<DetailRoute> { backStackEntry ->
                        val route: DetailRoute = backStackEntry.toRoute()
                        val album = kotlinx.serialization.json.Json.decodeFromString(Album.serializer(), route.albumJson)
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

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
    }
}
