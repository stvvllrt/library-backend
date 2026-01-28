package ru.stvvllrt.library.features.branches.data

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import ru.stvvllrt.library.data.Branches
import ru.stvvllrt.library.features.branches.domain.model.BranchResponse
import ru.stvvllrt.library.features.branches.domain.model.CreateBranchDto
import kotlin.time.Clock

class BranchRepositoryImpl : BranchRepository {
    override suspend fun createBranch(dto: CreateBranchDto): BranchResponse = suspendTransaction(){
        val newId = Branches.insert{
            it[Branches.name] = dto.name
            it[Branches.location] = dto.location
            it[Branches.phone] = dto.phone
            it[Branches.worktime] = dto.worktime
            it[Branches.createdTimestamp] = Clock.System.now()
        } get Branches.id
        Branches.selectAll().where { Branches.id eq newId }
            .map { row ->
                BranchResponse(
                    id = row[Branches.id],
                    name = row[Branches.name],
                    location = row[Branches.location],
                    phone = row[Branches.phone],
                    worktime = row[Branches.worktime],
                    createdTimestamp = row[Branches.createdTimestamp]
                )
            }.single()
    }

    override suspend fun getBranchById(id: Long): BranchResponse? = suspendTransaction(){
        Branches.selectAll().where { Branches.id eq id }
            .map { row ->
                BranchResponse(
                    id = row[Branches.id],
                    name = row[Branches.name],
                    location = row[Branches.location],
                    phone = row[Branches.phone],
                    worktime = row[Branches.worktime],
                    createdTimestamp = row[Branches.createdTimestamp]
                )
            }.singleOrNull()
    }
}