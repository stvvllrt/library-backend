package ru.stvvllrt.library.features.books.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class BookResponse(
    val id: Long,
    val title: String,
    val author: String,
    val isbn: String?,
    val description: String?,
    val genres: String?,
    val availability: Boolean,
    val createdTimestamp: Instant
)