package ru.stvvllrt.library.common

import org.mindrot.jbcrypt.BCrypt


fun generateInventoryCode(): String {
    val chars = ('A'..'Z') + ('0'..'9')
    val code = buildString(4) {
        repeat(4) {
            append(chars.random())
        }
    }
    return "INV-$code"
}

object PasswordHasher{
    fun hash(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun verify(password: String, hash: String): Boolean {
        return BCrypt.checkpw(password, hash)
    }
}