package com.erayerarslan.t_vac_kotlin.domain.usecase

import com.erayerarslan.t_vac_kotlin.core.Response
import com.erayerarslan.t_vac_kotlin.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String): Flow<Response<Void?>> =
        repository.resetPassword(email)
}