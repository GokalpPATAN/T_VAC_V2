package com.farukayata.t_vac_kotlin.domain.repository

import com.farukayata.t_vac_kotlin.core.Response
import com.farukayata.t_vac_kotlin.domain.repository.AuthenticationRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthenticationRepository {

    override suspend fun login(email: String, password: String): Flow<Response<AuthResult>> = flow {
        emit(Response.Loading)
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Login failed"))
        }
    }

    override suspend fun register(email: String, password: String): Flow<Response<AuthResult>> = flow {
        emit(Response.Loading)
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Registration failed"))
        }
    }

    override suspend fun resetPassword(email: String): Flow<Response<Void?>> = flow {
        emit(Response.Loading)
        try {
            val result = auth.sendPasswordResetEmail(email).await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Reset failed"))
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun userUid(): String =
        auth.currentUser?.uid ?: ""

    override suspend fun userEmail(): String =
        auth.currentUser?.email ?: ""

    override suspend fun isLoggedIn(): Boolean =
        auth.currentUser != null

    override suspend fun signInAnonymously(): Flow<Response<AuthResult>> = flow {
        emit(Response.Loading)
        try {
            val result = auth.signInAnonymously().await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Anonymous sign-in failed"))
        }
    }
}