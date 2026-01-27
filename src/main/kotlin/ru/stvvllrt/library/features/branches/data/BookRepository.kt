package ru.stvvllrt.library.features.branches.data

import ru.stvvllrt.library.features.branches.domain.model.Branch

interface BookRepository {
    suspend fun getBranches() : List<Branch>
    suspend fun getBranchById(id: Long) : Branch?
    suspend fun createBranch(name: String, location: String, phone: String?, worktime: String?) : Branch
}