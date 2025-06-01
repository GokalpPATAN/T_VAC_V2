package com.erayerarslan.t_vac_kotlin.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIService {
    @Headers("Content-Type: application/json")
    @POST("v1/images/generations")
    suspend fun generateImage(@Body request: ImageGenerationRequest): Response<ImageGenerationResponse>
}

data class ImageGenerationRequest(
    val prompt: String,
    val n: Int = 1,
    val size: String = "1024x1024",
    val model: String = "dall-e-2"
)

data class ImageGenerationResponse(
    val created: Long,
    val data: List<ImageData>
)

data class ImageData(
    val url: String
)