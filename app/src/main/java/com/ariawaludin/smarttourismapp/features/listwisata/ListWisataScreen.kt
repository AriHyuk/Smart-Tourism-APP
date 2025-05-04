package com.ariawaludin.smarttourismapp.features.listwisata


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class TouristPlace(
    val name: String,
    val location: String,
    val description: String
)

val sampleTouristPlaces = listOf(
    TouristPlace("Candi Borobudur", "Magelang", "Candi Buddha terbesar di dunia."),
    TouristPlace("Pantai Parangtritis", "Yogyakarta", "Pantai dengan ombak besar dan legenda mistis."),
    TouristPlace("Gunung Bromo", "Jawa Timur", "Gunung berapi aktif yang terkenal dengan sunrise-nya.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListWisataScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Daftar Wisata") })
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sampleTouristPlaces) { place ->
                TouristPlaceCard(place = place, onClick = {
                    // nanti bisa navigasi ke detail, misalnya:
                    // navController.navigate("detail/${place.name}")
                })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TouristPlaceCard(place: TouristPlace, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = place.name, style = MaterialTheme.typography.titleLarge)
            Text(text = place.location, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = place.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}
