package com.erayerarslan.t_vac_kotlin.domain.usecase

import com.erayerarslan.t_vac_kotlin.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke() = repository.logout()
}
