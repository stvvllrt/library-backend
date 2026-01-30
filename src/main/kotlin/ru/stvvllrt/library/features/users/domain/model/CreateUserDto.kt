package ru.stvvllrt.library.features.users.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto(
    val firstname: String,
    val lastname: String,
    val birthday: String,
    val phone: String,
    val email: String,
    val login: String,
    val password: String
)
