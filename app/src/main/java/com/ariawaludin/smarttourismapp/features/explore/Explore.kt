package com.ariawaludin.smarttourismapp.features.explore

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
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
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.navigation.NavController
import com.ariawaludin.smarttourismapp.R
import com.ariawaludin.smarttourismapp.model.MapDestination
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

fun getBitmapDescriptor(context: Context, @DrawableRes resId: Int): BitmapDescriptor {
    val drawable: Drawable = ContextCompat.getDrawable(context, resId)!!
    if (drawable is BitmapDrawable) {
        return BitmapDescriptorFactory.fromBitmap(drawable.bitmap)
    }
    val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@Composable
fun ExploreScreen(navController: NavController) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-6.1754, 106.8272), 10f)
    }

    val mapDestinations = remember {
        listOf(
            MapDestination("Hotel Indonesia", "Jakarta", LatLng(-6.1939, 106.8208), "hotel"),
            MapDestination("Plaza Indonesia", "Jakarta", LatLng(-6.1936, 106.8205), "restaurant"),
            MapDestination("Monas", "Jakarta", LatLng(-6.1754, 106.8272), "attraction"),
            MapDestination("Ancol", "Jakarta", LatLng(-6.1256, 106.8364), "activity"),
            MapDestination("Dufan", "Jakarta", LatLng(-6.1259, 106.8365), "activity"),
            MapDestination("Sate Senayan", "Jakarta", LatLng(-6.2246, 106.8025), "restaurant"),
            MapDestination("Hotel Mulia", "Jakarta", LatLng(-6.2185, 106.8017), "hotel"),
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
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                mapDestinations
                    .filter { activeFilters.isEmpty() || activeFilters.contains(it.type) }
                    .forEach { destination ->
                        Marker(
                            state = MarkerState(position = destination.position),
                            title = destination.name,
                            snippet = destination.location,
                            icon = getBitmapDescriptor(context, R.drawable.ic_launcher_foreground),
                            onClick = {
                                selectedDestination = destination
                                isInfoCardVisible = true
                                true
                            }
                        )
                    }
            }

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
