package com.example.inferno_bot.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inferno_bot.model.ChatMessage
import com.inferno_bot.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val repository = ChatRepository()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    init {
        _messages.value = listOf(
            ChatMessage(
                text = "Hello! I'm Inferno Bot powered by Gemini AI. How can I help you today?",
                isFromUser = false,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMessage = ChatMessage(
            text = text,
            isFromUser = true,
            timestamp = System.currentTimeMillis()
        )
        _messages.value = _messages.value + userMessage

        viewModelScope.launch {
            _isTyping.value = true

            repository.sendMessage(text).fold(
                onSuccess = { response ->
                    val botMessage = ChatMessage(
                        text = response,
                        isFromUser = false,
                        timestamp = System.currentTimeMillis()
                    )
                    _messages.value = _messages.value + botMessage
                },
                onFailure = { error ->
                    val errorMessage = ChatMessage(
                        text = "Sorry, I encountered an error: ${error.message}",
                        isFromUser = false,
                        timestamp = System.currentTimeMillis()
                    )
                    _messages.value = _messages.value + errorMessage
                }
            )

            _isTyping.value = false
        }
    }
}