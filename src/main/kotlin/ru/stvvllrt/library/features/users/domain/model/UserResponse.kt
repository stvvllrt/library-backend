package ru.stvvllrt.library.features.users.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class UserResponse(
    val id: Long,
    val firstname: String,
    val lastname: String,
    val birthday: String,
    val phone: String,
    val email: String,
    val login: String,
    val status: String,
    val createdTimestamp: Instant
)
