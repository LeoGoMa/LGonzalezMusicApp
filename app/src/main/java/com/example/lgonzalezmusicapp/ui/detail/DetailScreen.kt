package com.example.lgonzalezmusicapp.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.lgonzalezmusicapp.data.model.Album
import com.example.lgonzalezmusicapp.ui.home.MiniPlayer

@Composable
fun DetailScreen(
    album: Album,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = { MiniPlayer(album = album) },
        containerColor = Color(0xFFF3E5F5)
    ) { padding ->
        LazyColumn(
            modifier = modifier.fillMaxSize().padding(padding)
        ) {
            item { DetailHeader(album = album, onBackClick = onBackClick) }
            item { AboutAlbumCard(description = album.description ?: "No description available.") }
            item { ArtistChip(artistName = album.artist) }
            items(List(10) { it + 1 }) { trackNumber ->
                TrackItem(album = album, trackNumber = trackNumber)
            }
        }
    }
}

@Composable
fun DetailHeader(album: Album, onBackClick: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(album.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xFF4A148C).copy(alpha = 0.8f))
                    )
                )
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 48.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            IconButton(
                onClick = { },
                modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite", tint = Color.White)
            }
        }
        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(24.dp)
        ) {
            Text(text = album.title, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(text = album.artist, color = Color.LightGray, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)),
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", modifier = Modifier.size(32.dp))
                }
                OutlinedButton(
                    onClick = { },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(Color.White, Color.White))),
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Shuffle", modifier = Modifier.size(32.dp))
                }
            }
        }
    }
}

@Composable
fun AboutAlbumCard(description: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "About this album", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4A148C))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, color = Color.Gray, lineHeight = 22.sp)
        }
    }
}

@Composable
fun ArtistChip(artistName: String) {
    Surface(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Artist: ", fontWeight = FontWeight.Bold, color = Color(0xFF4A148C))
            Text(text = artistName, color = Color.Gray)
        }
    }
}

@Composable
fun TrackItem(album: Album, trackNumber: Int) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(album.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "${album.title} • Track $trackNumber", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = album.artist, color = Color.Gray, fontSize = 14.sp)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}