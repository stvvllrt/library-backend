package ru.stvvllrt.library.features.books.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import ru.stvvllrt.library.features.books.data.BookRepository
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
        get("/{id}"){
            val id = call.parameters["id"]?.toLongOrNull()
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

    }

}