package com.ariawaludin.smarttourismapp.features.camera

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.ariawaludin.smarttourismapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            imageBitmap = it.asImageBitmap()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Camera") },
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
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            } ?: Text("No image captured yet")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { cameraLauncher.launch(null) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Take Photo")
            }
        }
    }
}
