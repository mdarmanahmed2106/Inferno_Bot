package com.inferno_bot.repository

import com.example.inferno_bot.api.NetworkClient
import com.example.inferno_bot.model.Content
import com.example.inferno_bot.model.GeminiRequest
import com.example.inferno_bot.model.Part



class ChatRepository {
    private val geminiApi = NetworkClient.geminiApi

    suspend fun sendMessage(message: String): Result<String> {
        return try {
            val request = GeminiRequest(
                listOf(
                    Content(
                        listOf(
                            Part(text = message)
                        )
                    )
                )
            )

            val response = geminiApi.generateContent(
                //Put your API key here
                apiKey = "AIzaSyBKYkBDvEmbcSRJyOUy8bfCLJq7nflfEMo",
                request = request
            )

            if (response.isSuccessful) {
                val geminiResponse = response.body()
                val reply = geminiResponse?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                Result.success(reply ?: "No response from Gemini")
            } else {
                Result.failure(_root_ide_package_.kotlin.Exception("API Error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}