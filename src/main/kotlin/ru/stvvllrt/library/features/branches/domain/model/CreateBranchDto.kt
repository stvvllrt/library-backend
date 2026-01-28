package ru.stvvllrt.library.features.branches.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class CreateBranchDto(
    val name: String,
    val location: String,
    val phone: String?,
    val worktime: String?)