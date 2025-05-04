package com.ariawaludin.smarttourismapp.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ariawaludin.smarttourismapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    onMenuClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart Tourism") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Maps") },
                    label = { Text("Maps") },
                    selected = false,
                    onClick = { onMenuClick("maps") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Search Destination") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Banner
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Menu Fitur
            Text("Explore Features", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxHeight(),
                content = {
                    items(menuItems) { item ->
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { onMenuClick(item.route) }
                                .background(Color(0xFFE0E0E0), CircleShape)
                                .padding(16.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(item.icon, contentDescription = item.label, modifier = Modifier.size(32.dp))
                            Text(item.label, fontSize = 12.sp)
                        }
                    }
                }
            )
        }
    }
}
