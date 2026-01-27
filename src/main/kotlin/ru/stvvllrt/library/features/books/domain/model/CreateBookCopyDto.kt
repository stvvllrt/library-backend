package ru.stvvllrt.library.features.books.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class CreateBookCopyDto(
    val bookId: Long,
    val branchId: Long,
    val status: String,
    val condition: String,
    val notes: String,
)