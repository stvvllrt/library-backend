package ru.stvvllrt.library.features.librarians.presentation

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
import ru.stvvllrt.library.features.librarians.data.LibrariansRepository
import ru.stvvllrt.library.features.librarians.domain.model.CreateLibrarianDto

fun Route.librariansRoutes(librariansRepository: LibrariansRepository) {
    route("api/v1/librarians") {
        authenticate("auth-jwt") {
            post {
                try {
                    val principal = call.principal<JWTPrincipal>()
                    val isLibrarian = principal?.payload?.getClaim("isLibrarian")?.asBoolean() ?: false

                    if (!isLibrarian) {
                        call.respond(HttpStatusCode.Forbidden, "Доступ запрещен. Требуются права библиотекаря.")
                        return@post
                    }
                    val dto = call.receive<CreateLibrarianDto>()
                    val librarian = librariansRepository.createLibrarian(dto)
                    call.respond(HttpStatusCode.Created, librarian)
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        e.message ?: "Server faced errors. Please contact administrator."
                    )
                }
            }
        }
        get("/{id}"){
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val librarian = librariansRepository.getLibrarianById(id.toLong())
            if (librarian != null) {
                call.respond(HttpStatusCode.OK, librarian)
            } else {
                call.respond(HttpStatusCode.NotFound, "Librarian not found")
            }

        }
    }
}