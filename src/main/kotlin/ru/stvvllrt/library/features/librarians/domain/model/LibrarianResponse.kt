package ru.stvvllrt.library.features.librarians.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class LibrarianResponse(
    val id: Long,
    val userId: Long,
    val branchId: Long,
    val status: String,
    val hiredTimestamp: Instant
)
