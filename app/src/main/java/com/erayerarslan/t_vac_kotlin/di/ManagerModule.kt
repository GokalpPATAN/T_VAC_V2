package com.erayerarslan.t_vac_kotlin.di

import com.erayerarslan.t_vac_kotlin.model.SensorDataManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {

    @Provides
    @Singleton
    fun provideSensorDataManager(): SensorDataManager = SensorDataManager
}