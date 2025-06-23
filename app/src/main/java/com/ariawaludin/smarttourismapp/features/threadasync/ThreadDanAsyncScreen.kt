package com.ariawaludin.smarttourismapp.features.thread

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class QuotesResponse(
    @SerialName("result") val result: List<QuoteItem>
)

@kotlinx.serialization.Serializable
data class QuoteItem(
    val id: Int,
    val quotes: String,  // <- 'quotes' (bukan 'quote')
    val author: String
)


@Composable
fun ThreadDanAsyncScreen() {
    var isLoading by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }
    var quotes by remember { mutableStateOf<List<QuoteItem>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Client hanya sekali selama lifecycle
    val client = remember {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                isLoading = true
                error = null
                result = ""
                scope.launch {
                    try {
                        val response: List<QuoteItem> = client.get("https://test001-2425.vercel.app/api/quotes").body()
                        quotes = response
                        result = "Data berhasil di-load: ${quotes.size} quotes"
                    } catch (e: Exception) {
                        error = "Gagal mengambil quotes: ${e.message}"
                    } finally {
                        isLoading = false
                    }
                }

            }
        ) {
            Text("Load Quotes (Coroutine + API)")
        }
        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
            Text("Loading data...")
        }
        error?.let {
            Text(it, color = Color.Red)
        }
        if (result.isNotEmpty()) {
            Text(result, color = Color(0xFF388E3C))
        }
        Spacer(Modifier.height(24.dp))
        if (quotes.isNotEmpty()) {
            Text("Quotes:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            quotes.forEach { q ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE))
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(q.quotes, fontWeight = androidx.compose.ui.text.font.FontWeight.Medium)
                        Text("- ${q.author}", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = Color.Gray)
                    }
                }
            }

        }
    }
}
