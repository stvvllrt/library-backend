package ru.stvvllrt.library.features.books.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.Instant


@Serializable
data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val isbn: String?,
    val description: String?,
    val genres: String?,
    val createdTimestamp: Instant,
    val availability: Boolean
)

enum class BookCopyStatus {
    Available, Borrwed, Unavailable
}
enum class BookCopyCondition {
    Good, Fair, Poor
}

@Serializable
data class BookCopy(
    val id: Long,
    val bookId: Long,
    val branchId: Long,
    val inventoryCode: String,
    val status: String,
    val condition: String,
    val notes: String?,
    val addedTimestamp: Instant,
    val lastUpdateTimestamp: Instant
)

@Serializable
data class BookWithCopies(
    val book: Book,
    val copies: List<BookCopy>
)