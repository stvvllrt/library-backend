package ru.stvvllrt.library.features.books.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BookCopyResponse(
    val id: Long,
    val bookId: Long,
    val branchId: Long,
    val inventoryCode: String,
    val status: String,
    val condition: String,
    val notes: String,
    val addedTimestamp: Instant,
    val lastUpdateTimestamp: Instant
)