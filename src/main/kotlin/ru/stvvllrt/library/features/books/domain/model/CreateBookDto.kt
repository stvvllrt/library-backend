package ru.stvvllrt.library.features.books.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class CreateBookDto(
    val title: String,
    val author: String,
    val isbn: String?,
    val description: String?,
    val genres: String?,
    val availability: Boolean
)