//package com.erayerarslan.t_vac_kotlin.di
//
//import com.erayerarslan.t_vac_kotlin.domain.repository.FirebaseSensorDataRepository
//import com.erayerarslan.t_vac_kotlin.repository.SensorDataRepository
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class RepositoryModule {
//    @Binds
//    abstract fun bindSensorDataRepository(
//        impl: FirebaseSensorDataRepository
//    ): SensorDataRepository
//}