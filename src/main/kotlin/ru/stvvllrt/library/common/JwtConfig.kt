package ru.stvvllrt.library.common

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object JwtConfig {
    private val SECRET = System.getenv("JWT_SECRET")
        ?: throw IllegalStateException("JWT_SECRET environment variable not set")// В проде используй environment variable
    private const val ISSUER = "library-backend"
    private const val AUDIENCE = "library-users"
    private const val VALIDITY_MS = 36_000_00 * 24

    fun generateToken(userId: Long, login: String, isLibrarian: Boolean): String {
        return JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withClaim("userId", userId)
            .withClaim("login", login)
            .withClaim("isLibrarian", isLibrarian)
            .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY_MS))
            .sign(Algorithm.HMAC256(SECRET))
    }

    fun verifyToken(token: String) = JWT.require(Algorithm.HMAC256(SECRET))
        .withAudience(AUDIENCE)
        .withIssuer(ISSUER)
        .build()
        .verify(token)
}