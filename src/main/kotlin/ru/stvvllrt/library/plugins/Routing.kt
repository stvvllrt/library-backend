package ru.stvvllrt.library.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.stvvllrt.library.common.serviceRoutes
import ru.stvvllrt.library.features.books.data.BookRepositoryImpl
import ru.stvvllrt.library.features.books.presentation.bookRoutes
import ru.stvvllrt.library.features.branches.data.BranchRepositoryImpl
import ru.stvvllrt.library.features.branches.presentation.branchRoutes
import ru.stvvllrt.library.features.librarians.data.LibrariansRepositoryImpl
import ru.stvvllrt.library.features.librarians.presentation.librariansRoutes
import ru.stvvllrt.library.features.users.data.UsersRepositoryImpl
import ru.stvvllrt.library.features.users.presentation.usersRoutes

fun Application.configureRouting() {
    val booksRepository = BookRepositoryImpl()
    val branchesRepository = BranchRepositoryImpl()
    val usersRepository = UsersRepositoryImpl()
    val librariansRepository = LibrariansRepositoryImpl()
    routing {
        get("/") {
            call.respondText("Сервер работает, привет!")
        }
        bookRoutes(booksRepository)
        branchRoutes(branchesRepository)
        usersRoutes(usersRepository, librariansRepository)
        librariansRoutes(librariansRepository)
        serviceRoutes()
    }
}
