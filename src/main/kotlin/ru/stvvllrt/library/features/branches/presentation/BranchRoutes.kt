package ru.stvvllrt.library.features.branches.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import ru.stvvllrt.library.features.books.data.BookRepository
import ru.stvvllrt.library.features.books.domain.model.CreateBookDto
import ru.stvvllrt.library.features.branches.data.BranchRepository
import ru.stvvllrt.library.features.branches.domain.model.CreateBranchDto
import kotlin.text.toLongOrNull

fun Route.branchRoutes(branchRepository: BranchRepository) {
    route("/branches") {
        authenticate ( "auth-jwt" ){
            post {
                try {
                    val principal = call.principal<JWTPrincipal>()
                    val isLibrarian = principal?.payload?.getClaim("isLibrarian")?.asBoolean() ?: false

                    if (!isLibrarian) {
                        call.respond(HttpStatusCode.Forbidden, "Доступ запрещен.")
                        return@post
                    }

                    val createBranchDto = call.receive<CreateBranchDto>()
                    val newBranch = branchRepository.createBranch(createBranchDto)
                    call.respond(HttpStatusCode.Created, newBranch)
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        e.message ?: "Server faced errors. Please contact administrator."
                    )
                }
            }
        }
        get("/{id}"){
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing branch ID")
                return@get
            }
            val branch = branchRepository.getBranchById(id)
            if (branch != null) {
                call.respond(HttpStatusCode.OK, branch)
            } else {
                call.respond(HttpStatusCode.NotFound, "Branch not found")
            }
        }

    }

}