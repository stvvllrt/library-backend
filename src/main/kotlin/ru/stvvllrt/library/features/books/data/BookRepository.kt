package ru.stvvllrt.library.features.books.data

import ru.stvvllrt.library.features.books.domain.model.Book
import ru.stvvllrt.library.features.books.domain.model.BookCopy
import ru.stvvllrt.library.features.books.domain.model.BookWithCopies

interface BookRepository {
    suspend fun getAllBooks(): List<Book> // алярм - может быть много данных
    suspend fun getBookWithCopies(id: Long): BookWithCopies?
    suspend fun createBook(title: String, isbn: String?): Book
    suspend fun createBookCopy(bookId: Long, branchId: Long, inventoryCode: String): BookCopy
    suspend fun getCopiesByBookId(bookId: Long): List<BookCopy>
    suspend fun getCopiesByBranchId(branchId: Long): List<BookCopy>
}