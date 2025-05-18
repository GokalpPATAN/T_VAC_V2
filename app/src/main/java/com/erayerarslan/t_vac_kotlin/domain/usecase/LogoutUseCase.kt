package com.farukayata.t_vac_kotlin.domain.usecase

import com.farukayata.t_vac_kotlin.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke() = repository.logout()
}
