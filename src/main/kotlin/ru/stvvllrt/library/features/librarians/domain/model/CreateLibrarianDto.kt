package ru.stvvllrt.library.features.librarians.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateLibrarianDto(
    val userId: Long,
    val branchId: Long
)
