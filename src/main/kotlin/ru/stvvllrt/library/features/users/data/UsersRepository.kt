package ru.stvvllrt.library.features.users.data

import ru.stvvllrt.library.features.users.domain.model.CreateUserDto
import ru.stvvllrt.library.features.users.domain.model.UserResponse

interface UsersRepository {
    suspend fun createUser(dto: CreateUserDto, passwordHash: String): UserResponse
    suspend fun getUserById(id: Long): UserResponse?
    suspend fun getUserByLogin(login: String): UserResponse?
    suspend fun getPasswordHashByLogin(login: String): String?
}