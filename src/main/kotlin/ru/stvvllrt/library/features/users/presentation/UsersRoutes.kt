package ru.stvvllrt.library.features.users.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.jetbrains.exposed.v1.core.exposedLogger
import ru.stvvllrt.library.common.PasswordHasher
import ru.stvvllrt.library.features.users.data.UsersRepository
import ru.stvvllrt.library.features.users.domain.model.CreateUserDto
import ru.stvvllrt.library.features.users.domain.model.UserResponse

fun Route.usersRoutes(usersRepository: UsersRepository) {

    route("api/v1/users"){
        post{
            try{
                val dto = call.receive<CreateUserDto>()
                exposedLogger.info("Creating user: ${dto}")
                if (dto.login.isBlank()||dto.password.isBlank()){
                    call.respond(HttpStatusCode.BadRequest, "Логин и пароль не могут быть пустыми")
                    return@post
                }
                /* Ошибка при попытке создания первого пользователя в таблице
                if(usersRepository.getUserByLogin(dto.login) != null){
                    call.respond(HttpStatusCode.Conflict, "Пользователь с таким логином уже существует")
                    return@post
                }
                 */
                val passwordHash = PasswordHasher.hash(dto.password)
                val user = usersRepository.createUser(dto, passwordHash)
                call.respond(HttpStatusCode.Created, user)

            } catch (e: Exception) {
                exposedLogger.error(e.stackTraceToString())
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Server faced errors. Please contact administrator.")
            }
        }
        get("/{id}"){
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user: UserResponse? = usersRepository.getUserById(id.toLong())
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }
    }
}
