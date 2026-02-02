package ru.stvvllrt.library.data

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.timestamp

object Books : Table() {
    val id = long("id").autoIncrement()
    val title = varchar("title", 255)
    val author = varchar("author", 255)
    val isbn = varchar("isbn", 20).uniqueIndex().nullable()
    val description = text("description").nullable()
    val genres = text("genre").nullable()
    val createdTimestamp = timestamp("created_timestamp")
    val availability = bool("availability").default(true) // true - available, false - not available

    override val primaryKey = PrimaryKey(id)
}

object BooksStock : Table() {
    val id = long("id").autoIncrement()
    val bookId = long("book_id") references Books.id
    val branchId = long("branch_id") references Branches.id
    val inventoryCode = varchar("inventory_code", 50).uniqueIndex()
    val status = varchar("status", 20).default("AVAILABLE") // AVAILABLE, BORROWED, UNAVAILABLE
    val condition = varchar("condition", 20).default("GOOD") // GOOD, FAIR, POOR
    val notes = text("notes").nullable()
    val addedTimestamp = timestamp("added_timestamp")
    val lastUpdateTimestamp = timestamp("last_update_timestamp")

    override val primaryKey = PrimaryKey(id)
}

object Branches : Table(){
    val id = long("id").autoIncrement()
    val name = varchar("name", 255)
    val location = varchar("location", 255)
    val phone = varchar("phone", 255).nullable()
    val worktime = text("worktime").nullable()
    val createdTimestamp = timestamp("created_timestamp")

    override val primaryKey = PrimaryKey(id)
}

object Users : Table() {
    val id = long("id").autoIncrement()
    val firstname = varchar("firstname", 255)
    val lastname = varchar("lastname", 255)
    val birthday = varchar("birthday", length = 255)
    val phone = varchar("phone", 20)
    val email = varchar("email", 255).uniqueIndex()
    val login = varchar("login", 50).uniqueIndex()
    val password = varchar("password", 255)
    val status = varchar("status", 20).default("ACTIVE") // ACTIVE, INACTIVE
    val createdTimestamp = timestamp("created_timestamp")

    override val primaryKey = PrimaryKey(id)
}