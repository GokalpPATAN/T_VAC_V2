package com.erayerarslan.t_vac_kotlin.di


import com.erayerarslan.t_vac_kotlin.domain.repository.FirebaseSensorDataRepository
import com.erayerarslan.t_vac_kotlin.domain.repository.AuthenticationRepository
import com.erayerarslan.t_vac_kotlin.domain.repository.AuthenticationRepositoryImpl
import com.erayerarslan.t_vac_kotlin.domain.repository.BluetoothRepository
import com.erayerarslan.t_vac_kotlin.domain.repository.BluetoothRepositoryImpl
import com.erayerarslan.t_vac_kotlin.domain.repository.SensorDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindSensorDataRepo(
        impl: FirebaseSensorDataRepository
    ): SensorDataRepository

    @Binds
    abstract fun bindBluetoothRepo(
        impl: BluetoothRepositoryImpl
    ): BluetoothRepository

    @Binds
    abstract fun bindAuthRepo(
        impl: AuthenticationRepositoryImpl
    ): AuthenticationRepository
}