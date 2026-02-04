package ru.stvvllrt.library.features.librarians.data

import ru.stvvllrt.library.features.librarians.domain.model.CreateLibrarianDto
import ru.stvvllrt.library.features.librarians.domain.model.LibrarianResponse

interface LibrariansRepository {
    suspend fun createLibrarian(dto: CreateLibrarianDto): LibrarianResponse
    suspend fun getLibrarianById(id: Long): LibrarianResponse?
    suspend fun updateLibrarianStatus(id: Long, status: String): LibrarianResponse?
}