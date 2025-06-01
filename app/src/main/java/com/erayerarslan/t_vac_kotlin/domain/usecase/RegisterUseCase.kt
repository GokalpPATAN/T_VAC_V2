package com.erayerarslan.t_vac_kotlin.domain.usecase

import com.erayerarslan.t_vac_kotlin.core.Response
import com.erayerarslan.t_vac_kotlin.domain.repository.AuthenticationRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Response<AuthResult>> =
        repository.register(email, password)
}