package com.ariawaludin.smarttourismapp.features.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val menuItems = listOf(
    MenuItem("Wisata", Icons.Default.List, "listwisata"),
    MenuItem("Kamera", Icons.Default.CameraAlt, "camera"),
    MenuItem("Maps", Icons.Default.LocationOn, "maps"),
    MenuItem("Favorit", Icons.Default.Favorite, "favorite"),
    MenuItem("Berita", Icons.Default.Article, "news"),
    MenuItem("Kontak", Icons.Default.Phone, "contact")
)
