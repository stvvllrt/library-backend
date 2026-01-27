package ru.stvvllrt.library.features.books.data

import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import java.time.LocalDateTime
import org.jetbrains.exposed.v1.jdbc.*
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import ru.stvvllrt.library.data.BooksStock
import ru.stvvllrt.library.data.Books
import ru.stvvllrt.library.features.books.domain.model.BookCopyResponse
import ru.stvvllrt.library.features.books.domain.model.BookResponse
import ru.stvvllrt.library.features.books.domain.model.BookWithCopiesResponse
import ru.stvvllrt.library.features.books.domain.model.CreateBookDto
import kotlin.let
import kotlin.time.Clock

class BookRepositoryImpl : BookRepository {

    override suspend fun getAllBooks(): List<BookResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookWithCopies(id: Long): BookWithCopiesResponse? {
        TODO("Not yet implemented")
    }

    override suspend fun createBook(dto: CreateBookDto): BookResponse = suspendTransaction(){
        val newId = Books.insert {
            it[Books.title] = dto.title
            it[Books.author] = dto.author
            it[Books.isbn] = dto.isbn
            it[Books.description] = dto.description
            it[Books.genres] = dto.genres
            it[createdTimestamp] = Clock.System.now()
            it[availability] = true
        } get Books.id
        Books.selectAll().where { Books.id eq newId }
            .map { row ->
                BookResponse(
                    id = row[Books.id],
                    title = row[Books.title],
                    author = row[Books.author],
                    isbn = row[Books.isbn],
                    description = row[Books.description],
                    genres = row[Books.genres],
                    createdTimestamp = row[Books.createdTimestamp],
                    availability = row[Books.availability]
                )
            }.single()
    }

    override suspend fun getBookById(id: Long): BookResponse? = suspendTransaction() {
            Books.selectAll().where { Books.id eq id }
                .map { row ->
                    BookResponse(
                        id = row[Books.id],
                        title = row[Books.title],
                        author = row[Books.author],
                        isbn = row[Books.isbn],
                        description = row[Books.description],
                        genres = row[Books.genres],
                        createdTimestamp = row[Books.createdTimestamp],
                        availability = row[Books.availability]
                    )
                }.singleOrNull()
        }

    override suspend fun createBookCopy(bookId: Long, branchId: Long, inventoryCode: String): BookCopyResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getCopiesByBookId(bookId: Long): List<BookCopyResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getCopiesByBranchId(branchId: Long): List<BookCopyResponse> {
        TODO("Not yet implemented")
    }
}



