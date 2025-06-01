package com.erayerarslan.t_vac_kotlin.domain.usecase

import com.erayerarslan.t_vac_kotlin.domain.repository.AuthenticationRepository
import javax.inject.Inject

class UserUidUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(): String = repository.userUid()
}