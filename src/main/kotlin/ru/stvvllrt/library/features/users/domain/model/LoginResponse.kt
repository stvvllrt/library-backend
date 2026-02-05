package ru.stvvllrt.library.features.users.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val userId: Long,
    val login: String,
    val isLibrarian: Boolean
)
