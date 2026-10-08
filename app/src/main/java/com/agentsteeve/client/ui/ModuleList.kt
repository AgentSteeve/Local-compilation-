package com.agentsteeve.client.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Module(val name: String, val category: String)

@Composable
fun ModuleList() {
    val modules = remember {
        listOf(
            Module("KillAura", "Combat"),
            Module("Reach", "Combat"),
            Module("AutoClicker", "Combat"),
            Module("Velocity", "Combat"),
            Module("Fly", "Movement"),
            Module("Speed", "Movement"),
            Module("Bhop", "Movement"),
            Module("Jetpack", "Movement"),
            Module("ESP", "Visual"),
            Module("Tracers", "Visual"),
            Module("Fullbright", "Visual"),
            Module("NoFog", "Visual"),
            Module("Xray", "Visual"),
            Module("AutoTool", "World"),
            Module("Nuker", "World"),
            Module("ChestStealer", "World"),
            Module("Scaffold", "World"),
            Module("AutoTotem", "World")
        )
    }

    val enabledStates = remember {
        mutableStateMapOf<String, Boolean>().apply {
            modules.forEach { put(it.name, false) }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(modules) { module ->
            val isOn = enabledStates[module.name] == true
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface, RoundedCornerShape(8.dp))
                    .border(1.dp, Border, RoundedCornerShape(8.dp))
                    .clickable { enabledStates[module.name] = !isOn }
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = module.name,
                        color = if (isOn) Cyan else TextMain,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp
                    )
                    Text(
                        text = module.category,
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                }
                Switch(
                    checked = isOn,
                    onCheckedChange = { enabledStates[module.name] = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = InkBlack,
                        checkedTrackColor = Cyan,
                        uncheckedThumbColor = TextMuted,
                        uncheckedTrackColor = CyanDim
                    )
                )
            }
        }
    }
}
