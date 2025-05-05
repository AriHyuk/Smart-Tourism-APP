package com.ariawaludin.smarttourismapp.features.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ariawaludin.smarttourismapp.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.animation.AnimatedVisibility
import com.ariawaludin.smarttourismapp.features.home.MenuItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(
    onBackClick: () -> Unit,
    navigateToDestinationDetails: (String) -> Unit
) {
    val context = LocalContext.current

    // Default location (Bali)
    val defaultLocation = LatLng(-8.3405, 115.0920)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 10f)
    }

    var selectedDestination by remember { mutableStateOf<MapDestination?>(null) }
    var isInfoCardVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore on Map") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Google Map
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = true,
                    mapType = MapType.NORMAL
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = true
                )
            ) {
                // Add markers for destinations
                mapDestinations.forEach { destination ->
                    Marker(
                        state = MarkerState(position = destination.position),
                        title = destination.name,
                        snippet = destination.location,
                        icon = getBitmapDescriptor(context, R.drawable.ic_launcher_foreground), // Replace with your marker icon
                        onClick = {
                            selectedDestination = destination
                            isInfoCardVisible = true
                            true
                        }
                    )
                }
            }

            // Search bar at the top
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        "Search destinations on map...",
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }

            // Filter buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterButton(
                    icon = Icons.Default.Hotel,
                    label = "Hotels",
                    modifier = Modifier.weight(1f)
                )

                FilterButton(
                    icon = Icons.Default.Restaurant,
                    label = "Dining",
                    modifier = Modifier.weight(1f)
                )

                FilterButton(
                    icon = Icons.Default.Attractions,
                    label = "Attractions",
                    modifier = Modifier.weight(1f)
                )

                FilterButton(
                    icon = Icons.Default.LocalActivity,
                    label = "Activities",
                    modifier = Modifier.weight(1f)
                )
            }

            // Map control buttons
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    onClick = { /* TODO: Implement zoom in */ },
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Zoom In")
                }

                FloatingActionButton(
                    onClick = { /* TODO: Implement zoom out */ },
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Zoom Out")
                }

                FloatingActionButton(
                    onClick = { /* TODO: Implement current location */ },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = "My Location")
                }
            }

            // Info card for selected destination
            AnimatedVisibility(
                visible = isInfoCardVisible,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                selectedDestination?.let { destination ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Destination info
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            ) {
                                Text(
                                    destination.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Icon(
                                        Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Text(
                                        destination.location,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD700),
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Text(
                                        "${destination.rating} (${destination.reviewCount} reviews)",
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }

                            // View details button
                            Button(
                                onClick = { navigateToDestinationDetails(destination.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text("View Details")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    var isSelected by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .height(36.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.White
        ),
        onClick = { isSelected = !isSelected }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                label,
                color = if (isSelected) Color.White else Color.Black,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

// Utility function to convert vector drawable to bitmap descriptor for map markers
fun getBitmapDescriptor(context: Context, vectorResId: Int): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return BitmapDescriptorFactory.defaultMarker()
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

// Sample data class and data
data class MapDestination(
    val id: String,
    val name: String,
    val location: String,
    val rating: Double,
    val reviewCount: Int,
    val position: LatLng,
    val type: String // hotel, restaurant, attraction, activity
)

val mapDestinations = listOf(
    MapDestination(
        id = "dest1",
        name = "Kuta Beach",
        location = "Bali, Indonesia",
        rating = 4.7,
        reviewCount = 1234,
        position = LatLng(-8.7184, 115.1686),
        type = "attraction"
    ),
    MapDestination(
        id = "dest2",
        name = "Ubud Rice Terraces",
        location = "Bali, Indonesia",
        rating = 4.9,
        reviewCount = 876,
        position = LatLng(-8.5189, 115.2773),
        type = "attraction"
    ),
    MapDestination(
        id = "hotel1",
        name = "Grand Hyatt Bali",
        location = "Nusa Dua, Bali",
        rating = 4.8,
        reviewCount = 1569,
        position = LatLng(-8.7979, 115.2318),
        type = "hotel"
    ),
    MapDestination(
        id = "rest1",
        name = "Warung Made",
        location = "Seminyak, Bali",
        rating = 4.5,
        reviewCount = 985,
        position = LatLng(-8.6944, 115.1603),
        type = "restaurant"
    )
)