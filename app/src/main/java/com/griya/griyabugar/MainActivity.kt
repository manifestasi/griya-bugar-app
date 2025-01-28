package com.griya.griyabugar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.util.Fcm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val accessToken = Fcm.getAccessToken(this@MainActivity)
                withContext(Dispatchers.Main) {
                    Log.d("MainActivity", "Access Token: $accessToken")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("MainActivity", e.message.toString())
                }
            }
        }

        setContent {
            GriyaBugarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GriyaBugarApp()
                }
            }
        }
    }
}