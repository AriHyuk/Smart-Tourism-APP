package com.ariawaludin.smarttourismapp.features.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val icon: ImageVector,
    val label: String,
    val route: String,
    val color: Color
)

// Menu items for Explore Services section
val menuItems = listOf(
    MenuItem(
        icon = Icons.Filled.Explore,
        label = "Explore",
        route = "explore",
        color = Color(0xFF4CAF50)  // Green
    ),
    MenuItem(
        icon = Icons.Filled.Restaurant,
        label = "Dining",
        route = "dining",
        color = Color(0xFFF44336)  // Red
    ),
    MenuItem(
        icon = Icons.Filled.Hotel,
        label = "Hotels",
        route = "hotels",
        color = Color(0xFF2196F3)  // Blue
    ),
    MenuItem(
        icon = Icons.Filled.DirectionsBus,
        label = "Transport",
        route = "transport",
        color = Color(0xFFFF9800)  // Orange
    ),
    MenuItem(
        icon = Icons.Filled.Event,
        label = "Activities",
        route = "activities",
        color = Color(0xFF9C27B0)  // Purple
    ),
    MenuItem(
        icon = Icons.Filled.PhotoCamera,
        label = "CameraScreen",
        route = "camera",
        color = Color(0xFF9E9E9E)  // Gray
    ) ,
    MenuItem(icon = Icons.Filled.Sync,
    label = "AsyncTask/Thread",
    route = "thread_async",
    color = Color(0xFF00BCD4)
)


)