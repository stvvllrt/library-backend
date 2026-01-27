package ru.stvvllrt.library.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.stvvllrt.library.features.books.data.BookRepositoryImpl
import ru.stvvllrt.library.features.books.presentation.bookRoutes

fun Application.configureRouting() {
    val bookRepository = BookRepositoryImpl()
    routing {
        get("/") {
            call.respondText("Сервер работает, привет!")
        }
        bookRoutes(bookRepository)
    }
}
