package ru.stvvllrt.library.features.books.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import ru.stvvllrt.library.features.books.data.BookRepository
import ru.stvvllrt.library.features.books.domain.model.CreateBookCopyDto
import ru.stvvllrt.library.features.books.domain.model.CreateBookDto

fun Route.bookRoutes(bookRepository: BookRepository) {
    route("/books") {
        post{
            try{
                val createBookDto = call.receive<CreateBookDto>()
                val newBook = bookRepository.createBook(createBookDto)
                call.respond(HttpStatusCode.Created, newBook)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Server faced errors. Please contact administrator.")
            }
        }
        get("/{bookId}"){
            val id = call.parameters["bookId"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing book ID")
                return@get
            }
            val book = bookRepository.getBookById(id)
            if (book != null) {
                call.respond(HttpStatusCode.OK, book)
            } else {
                call.respond(HttpStatusCode.NotFound, "Book not found")
            }
        }
        route("/{bookId}/copies"){
            post{
                try{
                    val bookId = call.parameters["bookId"]?.toLongOrNull()
                    if (bookId == null) {
                        call.respond(HttpStatusCode.BadRequest, "Invalid or missing book ID")
                        return@post
                    }
                    val book = bookRepository.getBookById(bookId)
                    if (book == null) {
                        call.respond(HttpStatusCode.NotFound, "Book not found")
                        return@post
                    }else {
                        val createBookCopyDto = call.receive<CreateBookCopyDto>()
                        val newBookCopy = bookRepository.createBookCopy(createBookCopyDto.copy(bookId = bookId))
                        call.respond(HttpStatusCode.OK, newBookCopy)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Server faced errors. Please contact administrator.")
                }
            }
            get("/{copyId}"){
                val id = call.parameters["copyId"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing bookCopy ID")
                    return@get
                }
                val bookCopy = bookRepository.getBookCopyById(id)
                if (bookCopy != null) {
                    call.respond(HttpStatusCode.OK, bookCopy)
                } else {
                    call.respond(HttpStatusCode.NotFound, "BookCopy not found")
                }
            }
        }

    }

}