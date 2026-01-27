package ru.stvvllrt.library.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.stvvllrt.library.data.Books
import ru.stvvllrt.library.data.BooksStock
import ru.stvvllrt.library.data.Branches

fun Application.configureDatabases() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/library_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "1708"
    )

    transaction {
        SchemaUtils.create(Books, BooksStock, Branches)
        println("Таблицы Books, BooksStock, Branches созданы.")
    }
}