package ru.stvvllrt.library.features.books.data

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.*
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import ru.stvvllrt.library.common.generateInventoryCode
import ru.stvvllrt.library.data.Books
import ru.stvvllrt.library.data.BooksStock
import ru.stvvllrt.library.features.books.domain.model.BookCopyResponse
import ru.stvvllrt.library.features.books.domain.model.BookResponse
import ru.stvvllrt.library.features.books.domain.model.BookWithCopiesResponse
import ru.stvvllrt.library.features.books.domain.model.CreateBookCopyDto
import ru.stvvllrt.library.features.books.domain.model.CreateBookDto
import java.sql.SQLException
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

    override suspend fun getBookCopyById(id: Long): BookCopyResponse? = suspendTransaction() {
        BooksStock.selectAll().where { BooksStock.id eq id }
            .map { row ->
                BookCopyResponse(
                    id = row[BooksStock.id],
                    bookId = row[BooksStock.bookId],
                    branchId = row[BooksStock.branchId],
                    inventoryCode = row[BooksStock.inventoryCode],
                    status = row[BooksStock.status],
                    condition = row[BooksStock.condition],
                    notes = row[BooksStock.notes],
                    addedTimestamp = row[BooksStock.addedTimestamp],
                    lastUpdateTimestamp = row[BooksStock.lastUpdateTimestamp]
                )
            }.singleOrNull()
    }

    override suspend fun createBookCopy(dto: CreateBookCopyDto): BookCopyResponse = suspendTransaction(){
        var newId: Long
        try {
            newId = BooksStock.insert {
                it[BooksStock.bookId] = dto.bookId ?: throw IllegalArgumentException("bookId не может быть null")
                it[BooksStock.branchId] = dto.branchId
                it[BooksStock.condition] = dto.condition
                it[BooksStock.inventoryCode] = generateInventoryCode()
                it[BooksStock.status] = dto.status
                it[BooksStock.notes] = dto.notes
                it[BooksStock.addedTimestamp] = Clock.System.now()
                it[BooksStock.lastUpdateTimestamp] = Clock.System.now()
            } get BooksStock.id
        } catch (e: SQLException) {
            if (e.message?.contains("unique constraint") == true) {
                throw IllegalArgumentException("Книга с таким инвентарным кодом уже существует")
            } else {
                throw e
            }
        }
        BooksStock.selectAll().where { BooksStock.id eq newId }
            .map { row ->
                BookCopyResponse(
                    id = row[BooksStock.id],
                    bookId = row[BooksStock.bookId],
                    branchId = row[BooksStock.branchId],
                    inventoryCode = row[BooksStock.inventoryCode],
                    status = row[BooksStock.status],
                    condition = row[BooksStock.condition],
                    notes = row[BooksStock.notes],
                    addedTimestamp = row[BooksStock.addedTimestamp],
                    lastUpdateTimestamp = row[BooksStock.lastUpdateTimestamp]
                )
            }.single()
}

    override suspend fun getCopiesByBookId(bookId: Long): List<BookCopyResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getCopiesByBranchId(branchId: Long): List<BookCopyResponse> {
        TODO("Not yet implemented")
    }
}



