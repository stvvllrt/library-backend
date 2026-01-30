package ru.stvvllrt.library.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.stvvllrt.library.features.books.data.BookRepositoryImpl
import ru.stvvllrt.library.features.books.presentation.bookRoutes
import ru.stvvllrt.library.features.branches.data.BranchRepositoryImpl
import ru.stvvllrt.library.features.branches.presentation.branchRoutes
import ru.stvvllrt.library.features.users.data.UsersRepositoryImpl
import ru.stvvllrt.library.features.users.presentation.userRoutes

fun Application.configureRouting() {
    val booksRepository = BookRepositoryImpl()
    val branchesRepository = BranchRepositoryImpl()
    val usersRepository = UsersRepositoryImpl()
    routing {
        get("/") {
            call.respondText("Сервер работает, привет!")
        }
        bookRoutes(booksRepository)
        branchRoutes(branchesRepository)
        userRoutes(usersRepository)

    }
}
