package com.example.temperaturesensor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class MainActivity : ComponentActivity() {

    // TODO: Поменяйте на ваш реальный URL, возвращающий JSON { "temperature": число }
    private val apiUrl = "https://api.example.com/temperature"

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureScreen()
        }
    }

    @Composable
    fun TemperatureScreen() {
        // Состояние для текста: пока "Загрузка...", потом либо "Температура: XX.X °C", либо сообщение об ошибке
        var displayText by remember { mutableStateOf("Загрузка...") }
        // Флаг, чтобы запрос отправлялся только один раз
        var hasLaunched by remember { mutableStateOf(false) }

        // Запускаем корутину один раз при первом составлении (LaunchedEffect(Unit))
        if (!hasLaunched) {
            LaunchedEffect(Unit) {
                hasLaunched = true
                // Переходим в IO-диспетчер, чтобы выполнить сетевой запрос
                val resultString: String? = withContext(Dispatchers.IO) {
                    try {
                        val request = Request.Builder()
                            .url(apiUrl)
                            .get()
                            .build()
                        client.newCall(request).execute().use { response ->
                            if (!response.isSuccessful) throw IOException("Unexpected code $response")
                            response.body?.string()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }

                // После возвращения из IO-диспетчера анализируем результат
                if (resultString != null) {
                    try {
                        val jsonObj = JSONObject(resultString)
                        val tempValue = jsonObj.getDouble("temperature")
                        displayText = "Температура: $tempValue °C"
                    } catch (e: Exception) {
                        e.printStackTrace()
                        displayText = "Неверный формат данных"
                    }
                } else {
                    displayText = "Ошибка загрузки"
                }
            }
        }

        // UI: текст по центру экрана
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayText,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
