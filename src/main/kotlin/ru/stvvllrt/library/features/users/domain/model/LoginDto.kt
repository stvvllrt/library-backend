package ru.stvvllrt.library.features.users.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val login: String,
    val password: String
)
