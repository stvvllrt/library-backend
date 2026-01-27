package ru.stvvllrt.library.features.branches.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Branch(
    val id: Long,
    val name: String,
    val location: String,
    val phone: String?,
    val worktime: String?,
    val createdTimestamp: Instant)