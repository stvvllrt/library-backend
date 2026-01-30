package ru.stvvllrt.library.features.users.data

import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import ru.stvvllrt.library.features.users.domain.model.CreateUserDto
import ru.stvvllrt.library.features.users.domain.model.UserResponse
import ru.stvvllrt.library.data.Users
import org.jetbrains.exposed.v1.jdbc.*
import org.jetbrains.exposed.v1.core.eq
import kotlin.time.Clock

class UsersRepositoryImpl : UsersRepository{
    override suspend fun createUser(
        dto: CreateUserDto,
        passwordHash: String
    ): UserResponse = suspendTransaction{
        val newId = Users.insertAndGetId{
            it[Users.firstname] = dto.firstname
            it[Users.lastname] = dto.lastname
            it[Users.birthday] = dto.birthday
            it[Users.phone] = dto.phone
            it[Users.email] = dto.email
            it[Users.login] = dto.login
            it[Users.password] = passwordHash
            it[Users.status] = "ACTIVE"
            it[Users.createdTimestamp] = Clock.System.now()
        }
        UserResponse(
            id = newId.value,  // ← КЛЮЧЕВОЕ .value!
            firstname = dto.firstname,
            lastname = dto.lastname,
            birthday = dto.birthday,
            phone = dto.phone,
            email = dto.email,
            login = dto.login,
            status = "ACTIVE",
            createdTimestamp = Clock.System.now()
        )
    }

    override suspend fun getUserById(id: Long): UserResponse = suspendTransaction{
        Users.selectAll().where { Users.id eq id }
            .map { row ->
                UserResponse(
                    id = row[Users.id],
                    firstname =  row[Users.firstname],
                    lastname = row[Users.lastname],
                    birthday = row[Users.birthday],
                    phone = row[Users.phone],
                    email = row[Users.email],
                    login = row[Users.login],
                    status = row[Users.status],
                    createdTimestamp = row[Users.createdTimestamp]
                )
            }.single()
    }

    override suspend fun getUserByLogin(login: String): UserResponse? = suspendTransaction{
        Users.selectAll().where { Users.login eq login }
            .map { row ->
                UserResponse(
                    id = row[Users.id],
                    firstname =  row[Users.firstname],
                    lastname = row[Users.lastname],
                    birthday = row[Users.birthday],
                    phone = row[Users.phone],
                    email = row[Users.email],
                    login = row[Users.login],
                    status = row[Users.status],
                    createdTimestamp = row[Users.createdTimestamp]
                )
            }.single()
    }
}
