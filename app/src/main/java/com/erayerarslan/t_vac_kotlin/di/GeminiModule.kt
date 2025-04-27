package com.farukayata.t_vac_kotlin.di

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.farukayata.t_vac_kotlin.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeminiModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        Log.d("GEMINI_KEY", "Key: ${BuildConfig.GEMINI_API_KEY}")
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            //modelName = "gemini-pro", // metin tabanlı model
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }
}

/* -> ile geminiay çağırıcaz
@Inject constructor(
    private val generativeModel: GenerativeModel
)

 */