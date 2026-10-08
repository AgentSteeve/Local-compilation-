package com.agentsteeve.client

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agentsteeve.client.ui.AgentTheme
import com.agentsteeve.client.ui.ModuleList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgentTheme {
                MainScreen(
                    onStart = { startProxyService() },
                    onStop = { stopProxyService() }
                )
            }
        }
    }

    private fun startProxyService() {
        val intent = Intent(this, ProxyService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun stopProxyService() {
        stopService(Intent(this, ProxyService::class.java))
    }
}

@Composable
fun MainScreen(onStart: () -> Unit, onStop: () -> Unit) {
    var running by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0B1220)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "AgentSteeve",
                color = Color(0xFF22D3EE),
                fontFamily = FontFamily.Monospace,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "proxy  ·  localhost:19132",
                color = Color(0xFF8A9BB0),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (running) onStop() else onStart()
                    running = !running
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (running) Color(0xFF3A4A5E) else Color(0xFF22D3EE),
                    contentColor = Color(0xFF0B1220)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (running) "ARRÊTER LE PROXY" else "DÉMARRER LE PROXY")
            }

            Spacer(Modifier.height(20.dp))

            ModuleList()
        }
    }
}
