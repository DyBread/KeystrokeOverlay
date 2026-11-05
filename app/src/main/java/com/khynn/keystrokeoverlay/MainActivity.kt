package com.khynn.keystrokeoverlay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF10131B)
private val Panel = Color(0xFF1B2030)
private val Accent = Color(0xFF00E5A8)
private val KeyColor = Color(0xFF30394D)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Accent,
                    background = Bg,
                    surface = Panel
                )
            ) {
                MainScreen(
                    onOverlayPermission = {
                        val intent = Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    },
                    hasOverlayPermission = {
                        Settings.canDrawOverlays(this)
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    onOverlayPermission: () -> Unit,
    hasOverlayPermission: () -> Boolean
) {
    var permission by remember {
        mutableStateOf(hasOverlayPermission())
    }

    LaunchedEffect(Unit) {
        permission = hasOverlayPermission()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Bg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Column {
                Text(
                    "KEYSTROKE",
                    color = Accent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "OVERLAY",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    "Gaming Input Visualizer",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

            HudPreview()

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Panel
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "SYSTEM PERMISSIONS",
                        color = Accent,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Display over other apps",
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            if (permission) "GRANTED" else "MISSING",
                            color = if (permission) Accent else Color.Red,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = onOverlayPermission,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Accent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("GRANT OVERLAY PERMISSION")
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Panel
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "INPUT ENGINE",
                        color = Accent,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Keyboard: Not connected",
                        color = Color.LightGray
                    )
                    Text(
                        "Mouse: Not connected",
                        color = Color.LightGray
                    )
                    Text(
                        "Controller: Not connected",
                        color = Color.LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "KEYSTROKE OVERLAY • VERSION 1.0.0",
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun HudPreview() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Panel
        ),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "HUD PREVIEW",
                    color = Accent,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "DEMO",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }

            KeyRow(listOf("W"))
            KeyRow(listOf("A", "S", "D"))
            KeyRow(listOf("SPACE", "SHIFT"))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KeyButton("LMB")
                KeyButton("RMB")
                KeyButton("MMB")
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KeyButton("A")
                KeyButton("B")
                KeyButton("X")
                KeyButton("Y")
            }
        }
    }
}

@Composable
fun KeyRow(keys: List<String>) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        keys.forEach { key ->
            KeyButton(key)
        }
    }
}

@Composable
fun KeyButton(label: String) {
    Surface(
        color = KeyColor,
        shape = RoundedCornerShape(9.dp),
        modifier = Modifier.size(
            width = if (label.length > 3) 75.dp else 52.dp,
            height = 42.dp
        )
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                label,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
