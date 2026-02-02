package ru.stvvllrt.library.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.stvvllrt.library.data.Books
import ru.stvvllrt.library.data.BooksStock
import ru.stvvllrt.library.data.Branches
import ru.stvvllrt.library.data.Users

fun Application.configureDatabases() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5433/library-database",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "1708"
    )

    transaction {
        SchemaUtils.create(Books, BooksStock, Branches, Users)
        println("Таблицы Books, BooksStock, Branches, Users созданы.")
    }
}