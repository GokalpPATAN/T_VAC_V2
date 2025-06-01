package com.erayerarslan.t_vac_kotlin.domain.usecase

import com.erayerarslan.t_vac_kotlin.domain.repository.AuthenticationRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(): Boolean = repository.isLoggedIn()
}