package com.ariawaludin.smarttourismapp.features.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    var darkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    var useGps by remember { mutableStateOf(true) }
    var autoUpdate by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("App Preferences", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))

            SettingCheckboxItem(
                title = "Dark Mode",
                checked = darkMode,
                onCheckedChange = { darkMode = it }
            )
            SettingCheckboxItem(
                title = "Enable Notifications",
                checked = notifications,
                onCheckedChange = { notifications = it }
            )
            SettingCheckboxItem(
                title = "Use GPS Location",
                checked = useGps,
                onCheckedChange = { useGps = it }
            )
            SettingCheckboxItem(
                title = "Auto-Update App",
                checked = autoUpdate,
                onCheckedChange = { autoUpdate = it }
            )
        }
    }
}

@Composable
fun SettingCheckboxItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, modifier = Modifier.weight(1f))
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
    }
}
