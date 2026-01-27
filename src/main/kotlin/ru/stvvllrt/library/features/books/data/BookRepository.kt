package ru.stvvllrt.library.features.books.data

import ru.stvvllrt.library.features.books.domain.model.BookCopyResponse
import ru.stvvllrt.library.features.books.domain.model.BookResponse
import ru.stvvllrt.library.features.books.domain.model.BookWithCopiesResponse
import ru.stvvllrt.library.features.books.domain.model.CreateBookDto

interface BookRepository {
    suspend fun createBook(dto: CreateBookDto): BookResponse
    suspend fun createBookCopy(bookId: Long, branchId: Long, inventoryCode: String): BookCopyResponse
    suspend fun getBookById(id: Long): BookResponse?
    suspend fun getCopiesByBookId(bookId: Long): List<BookCopyResponse>
    suspend fun getCopiesByBranchId(branchId: Long): List<BookCopyResponse>
    suspend fun getAllBooks(): List<BookResponse> // алярм - может быть много данных
    suspend fun getBookWithCopies(id: Long): BookWithCopiesResponse?
}