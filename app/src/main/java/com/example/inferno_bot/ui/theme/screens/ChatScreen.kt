package com.example.inferno_bot.ui.theme.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inferno_bot.ViewModel.ChatViewModel
import com.example.inferno_bot.ui.theme.DarkSurface
import com.example.inferno_bot.ui.theme.DarkSurfaceVariant
import com.example.inferno_bot.ui.theme.TextSecondary
import com.example.inferno_bot.ui.theme.TextTertiary
import com.example.inferno_bot.ui.theme.TextPrimary
import com.example.inferno_bot.ui.theme.components.MessageBubble
import com.example.inferno_bot.ui.theme.components.TypingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // gentle auto-scroll to bottom
    LaunchedEffect(messages.size, isTyping) {
        if (messages.isNotEmpty()) {
            kotlinx.coroutines.delay(100)
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0B0B0C), Color(0xFF18181A))
                )
            )
    ) {
        // 🔥 Background aura behind everything (blurred)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(180.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF6B00).copy(alpha = 0.45f),
                            Color(0xFFB22222).copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Header area with gradient title and soft divider
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkSurfaceVariant, RoundedCornerShape(20.dp))
                    .padding(top = 35.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFFF6B00), Color(0xFFB22222))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🔥", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Inferno Bot",
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFFF6B00), Color(0xFFFF3D00), Color(0xFFFFB347))
                            ),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(color = Color(0x88FF6B00), blurRadius = 6f)
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = if (isTyping) "typing..." else "Online",
                        style = TextStyle(color = TextSecondary, fontSize = 12.sp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // soft glowing divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color.Transparent, Color(0xFFFF6B00).copy(alpha = 0.55f), Color.Transparent)
                            )
                        )
                        .blur(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Messages area (takes remaining space)
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(messages, key = { it.timestamp }) { message ->
                        MessageBubble(message = message)
                    }
                }

                // show typing indicator above the input, anchored to bottom start of message area
                if (isTyping) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 12.dp, bottom = 88.dp) // keep it above input
                    ) {
                        TypingIndicator()
                    }
                }
            }

            // Input area fixed at bottom
            // Row for input + send
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 6.dp, start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // rounded glow container behind text field (only behind width of text field)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    // inner glow element (keeps shape rounded)
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(40.dp))
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color(0xFFFF6B00).copy(alpha = 0.18f), Color.Transparent),
                                    radius = 420f
                                )
                            )
                            .blur(28.dp)
                    )

                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(40.dp)),
                        placeholder = { Text("Type a message...", color = TextTertiary) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedBorderColor = Color(0xFFFF6B00),
                            unfocusedBorderColor = DarkSurfaceVariant,
                            cursorColor = Color(0xFFFF6B00)
                        ),
                        shape = RoundedCornerShape(40.dp),
                        maxLines = 5,
                        enabled = !isTyping
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // glowing send button: a subtle glow box behind the FAB
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    // glow layer
                    if (inputText.isNotBlank()) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF6B00).copy(alpha = 0.25f))
                                .blur(18.dp)
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                viewModel.sendMessage(inputText)
                                inputText = ""
                            }
                        },
                        containerColor = if (inputText.isBlank()) DarkSurfaceVariant else Color(0xFFFF6B00),
                        contentColor = Color.White,
                        modifier = Modifier.size(56.dp),
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
        }
    }
}
