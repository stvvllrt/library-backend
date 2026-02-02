package ru.stvvllrt.library.features.users.data

import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import ru.stvvllrt.library.features.users.domain.model.CreateUserDto
import ru.stvvllrt.library.features.users.domain.model.UserResponse
import ru.stvvllrt.library.data.Users
import org.jetbrains.exposed.v1.jdbc.*
import org.jetbrains.exposed.v1.core.eq
import kotlin.collections.get
import kotlin.text.get
import kotlin.text.set
import kotlin.time.Clock

class UsersRepositoryImpl : UsersRepository{
    override suspend fun createUser(
        dto: CreateUserDto,
        passwordHash: String
    ): UserResponse = suspendTransaction(){
        Users.insert {
            it[firstname] = dto.firstname
            it[lastname] = dto.lastname
            it[birthday] = dto.birthday
            it[phone] = dto.phone
            it[email] = dto.email
            it[login] = dto.login
            it[password] = passwordHash
            it[status] = "ACTIVE"
            it[createdTimestamp] = Clock.System.now()
        }
        Users.selectAll().where { Users.login eq dto.login }
            .map { row ->
                UserResponse(
                    id = row[Users.id],
                    firstname = row[Users.firstname],
                    lastname = row[Users.lastname],
                    birthday = row[Users.birthday],
                    phone = row[Users.phone],
                    email = row[Users.email],
                    login = row[Users.login],
                    status = row[Users.status],
                    createdTimestamp = row[Users.createdTimestamp]
                )
            }.single() // Используем single(), так как уверены, что пользователь существует

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
