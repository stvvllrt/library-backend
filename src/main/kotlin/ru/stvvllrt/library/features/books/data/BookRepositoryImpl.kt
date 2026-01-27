package ru.stvvllrt.library.features.books.data

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import ru.stvvllrt.library.data.BooksStock
import ru.stvvllrt.library.data.Books
import ru.stvvllrt.library.features.books.domain.model.Book
import ru.stvvllrt.library.features.books.domain.model.BookCopy
import ru.stvvllrt.library.features.books.domain.model.BookWithCopies

class BookRepositoryImpl : BookRepository {

    override suspend fun getAllBooks(): List<Book> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookWithCopies(id: Long): BookWithCopies? {
        TODO("Not yet implemented")
    }

    override suspend fun createBook(title: String, isbn: String?): Book {
        TODO("Not yet implemented")
    }

    override suspend fun createBookCopy(bookId: Long, branchId: Long, inventoryCode: String): BookCopy {
        TODO("Not yet implemented")
    }

    override suspend fun getCopiesByBookId(bookId: Long): List<BookCopy> {
        TODO("Not yet implemented")
    }

    override suspend fun getCopiesByBranchId(branchId: Long): List<BookCopy> {
        TODO("Not yet implemented")
    }
}



