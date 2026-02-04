package ru.stvvllrt.library.features.librarians.data

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import ru.stvvllrt.library.data.Librarians
import ru.stvvllrt.library.features.librarians.domain.model.CreateLibrarianDto
import ru.stvvllrt.library.features.librarians.domain.model.LibrarianResponse
import kotlin.time.Clock

class LibrariansRepositoryImpl : LibrariansRepository {
    override suspend fun createLibrarian(dto: CreateLibrarianDto): LibrarianResponse = suspendTransaction {
        Librarians.insert {
            it[userId] = dto.userId
            it[branchId] = dto.branchId
            it[status] = "ACTIVE"
            it[hiredTimestamp] = Clock.System.now()
        }
        Librarians.selectAll().where { Librarians.userId eq dto.userId }
            .map { row ->
                LibrarianResponse(
                    id = row[Librarians.id],
                    userId = row[Librarians.userId],
                    branchId = row[Librarians.branchId],
                    status = row[Librarians.status],
                    hiredTimestamp = row[Librarians.hiredTimestamp]
                )
            }.single()
    }

    override suspend fun getLibrarianById(id: Long): LibrarianResponse = suspendTransaction{
        Librarians.selectAll().where { Librarians.userId eq id }
            .map { row ->
                LibrarianResponse(
                    id = row[Librarians.id],
                    userId = row[Librarians.userId],
                    branchId = row[Librarians.branchId],
                    status = row[Librarians.status],
                    hiredTimestamp = row[Librarians.hiredTimestamp]
                )
            }.single()
    }

    override suspend fun updateLibrarianStatus(id: Long, status: String): LibrarianResponse? {
        TODO("Not yet implemented")
    }
}