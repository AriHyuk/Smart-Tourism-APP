package com.ariawaludin.smarttourismapp.features.explore

import android.content.Context
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import com.ariawaludin.smarttourismapp.model.MapDestination

@Composable
fun ExploreScreen(navController: NavController) {
    val context = LocalContext.current

    // Data destinasi
    val mapDestinations = remember {
        listOf(
            MapDestination("Hotel Indonesia", "Jakarta", GeoPoint(-6.1939, 106.8208), "hotel"),
            MapDestination("Plaza Indonesia", "Jakarta", GeoPoint(-6.1936, 106.8205), "restaurant"),
            MapDestination("Monas", "Jakarta", GeoPoint(-6.1754, 106.8272), "attraction"),
            MapDestination("Ancol", "Jakarta", GeoPoint(-6.1256, 106.8364), "activity"),
            MapDestination("Dufan", "Jakarta", GeoPoint(-6.1259, 106.8365), "activity"),
            MapDestination("Sate Senayan", "Jakarta", GeoPoint(-6.2246, 106.8025), "restaurant"),
            MapDestination("Hotel Mulia", "Jakarta", GeoPoint(-6.2185, 106.8017), "hotel"),
        )
    }

    var selectedDestination by remember { mutableStateOf<MapDestination?>(null) }
    var isInfoCardVisible by remember { mutableStateOf(false) }

    var activeFilters by remember { mutableStateOf(setOf<String>()) }
    fun toggleFilter(type: String) {
        activeFilters = if (activeFilters.contains(type)) {
            activeFilters - type
        } else {
            activeFilters + type
        }
    }

    // Setup osmdroid config (WAJIB)
    DisposableEffect(context) {
        Configuration.getInstance().load(context, context.getSharedPreferences("osm_prefs", Context.MODE_PRIVATE))
        onDispose { }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterButton(Icons.Default.Hotel, "Hotels", "hotel", activeFilters.contains("hotel"), ::toggleFilter, Modifier.weight(1f))
            FilterButton(Icons.Default.Restaurant, "Dining", "restaurant", activeFilters.contains("restaurant"), ::toggleFilter, Modifier.weight(1f))
            FilterButton(Icons.Default.Place, "Attractions", "attraction", activeFilters.contains("attraction"), ::toggleFilter, Modifier.weight(1f))
            FilterButton(Icons.Default.SportsSoccer, "Activities", "activity", activeFilters.contains("activity"), ::toggleFilter, Modifier.weight(1f))
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // osmdroid MapView via AndroidView
            androidx.compose.ui.viewinterop.AndroidView(
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        controller.setZoom(12.0)
                        controller.setCenter(GeoPoint(-6.1754, 106.8272)) // Monas

                        // Hapus marker sebelumnya
                        overlays.clear()

                        // Tambah marker sesuai filter
                        mapDestinations
                            .filter { activeFilters.isEmpty() || activeFilters.contains(it.type) }
                            .forEach { dest ->
                                val marker = Marker(this)
                                marker.position = dest.position
                                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                marker.title = dest.name
                                marker.subDescription = dest.location
                                marker.setOnMarkerClickListener { m, _ ->
                                    selectedDestination = dest
                                    isInfoCardVisible = true
                                    true
                                }
                                overlays.add(marker)
                            }
                    }
                },
                update = { mapView ->
                    // update marker kalau filter berubah
                    mapView.overlays.clear()
                    mapDestinations
                        .filter { activeFilters.isEmpty() || activeFilters.contains(it.type) }
                        .forEach { dest ->
                            val marker = Marker(mapView)
                            marker.position = dest.position
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            marker.title = dest.name
                            marker.subDescription = dest.location
                            marker.setOnMarkerClickListener { m, _ ->
                                selectedDestination = dest
                                isInfoCardVisible = true
                                true
                            }
                            mapView.overlays.add(marker)
                        }
                },
                modifier = Modifier.fillMaxSize()
            )

            if (isInfoCardVisible && selectedDestination != null) {
                DestinationInfoCard(
                    destination = selectedDestination!!,
                    onClose = { isInfoCardVisible = false },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}

// FilterButton & DestinationInfoCard sama seperti kode kamu sebelumnya
@Composable
fun FilterButton(
    icon: ImageVector,
    label: String,
    type: String,
    isSelected: Boolean,
    onToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(36.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(18.dp))
            .clickable { onToggle(type) },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.White
        )
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

@Composable
fun DestinationInfoCard(
    destination: MapDestination,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = destination.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Location: ${destination.location}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
