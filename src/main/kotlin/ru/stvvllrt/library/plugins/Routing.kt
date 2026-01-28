package ru.stvvllrt.library.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.stvvllrt.library.features.books.data.BookRepositoryImpl
import ru.stvvllrt.library.features.books.presentation.bookRoutes
import ru.stvvllrt.library.features.branches.data.BranchRepositoryImpl
import ru.stvvllrt.library.features.branches.presentation.branchRoutes

fun Application.configureRouting() {
    val bookRepository = BookRepositoryImpl()
    val branchRepository = BranchRepositoryImpl()
    routing {
        get("/") {
            call.respondText("Сервер работает, привет!")
        }
        bookRoutes(bookRepository)
        branchRoutes(branchRepository)
    }
}
