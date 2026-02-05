package ru.stvvllrt.library.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

private val SECRET = System.getenv("JWT_SECRET")
    ?: throw IllegalStateException("JWT_SECRET environment variable not set")

fun Application.configureSecurity() {
    authentication {
        jwt("auth-jwt") {
            verifier(
                JWT.require(Algorithm.HMAC256(SECRET))
                    .withAudience("library-users")
                    .withIssuer("library-backend")
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("userId").asLong() != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
