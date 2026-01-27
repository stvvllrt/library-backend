package ru.stvvllrt.library.features.books.domain.model

import kotlinx.serialization.Serializable
import java.awt.print.Book
import kotlin.time.Instant


@Serializable
data class BookWithCopiesResponse(
    val book: BookResponse,
    val copies: List<BookCopyResponse>)