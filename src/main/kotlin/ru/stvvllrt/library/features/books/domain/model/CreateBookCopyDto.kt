package ru.stvvllrt.library.features.books.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateBookCopyDto(
    val bookId: Long? = null,
    val branchId: Long,
    val status: String,
    val condition: String,
    val notes: String?,
    val inventoryCode: String? = null
)
