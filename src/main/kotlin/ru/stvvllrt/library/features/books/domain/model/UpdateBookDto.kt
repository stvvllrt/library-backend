package ru.stvvllrt.library.features.books.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateBookDto(
    val title: String,
    val author: String,
    val isbn: String?,
    val description: String?,
    val genres: String?,
    val availability: Boolean
)