package com.agentsteeve.client

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.agentsteeve.client.ui.AgentTheme
import com.agentsteeve.client.ui.ModuleList

class MainActivity : ComponentActivity() {

    private val notifPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // demande la permission notifications (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

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
        // demande la permission overlay si pas accordée
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            !Settings.canDrawOverlays(this)
        ) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
            return
        }

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
