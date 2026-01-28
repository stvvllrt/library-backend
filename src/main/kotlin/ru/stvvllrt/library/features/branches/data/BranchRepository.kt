package ru.stvvllrt.library.features.branches.data

import ru.stvvllrt.library.features.branches.domain.model.BranchResponse
import ru.stvvllrt.library.features.branches.domain.model.CreateBranchDto

interface BranchRepository {
    suspend fun createBranch(dto: CreateBranchDto): BranchResponse
    suspend fun getBranchById(id: Long): BranchResponse?
}