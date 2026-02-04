package ru.stvvllrt.library.common


import kotlin.time.Clock
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import net.datafaker.Faker
import ru.stvvllrt.library.data.*
import java.util.*
import kotlin.random.Random
import kotlin.repeat

object TestDataGenerator {

    private val faker = Faker(Locale("ru"))

    private val genres_list = listOf(
        "Классика", "Роман", "Детектив", "Фантастика",
        "Философия", "Драма", "Сатира", "Поэзия", "Приключения"
    )

    private val conditions = listOf("GOOD", "FAIR", "POOR")
    private val statuses = listOf("AVAILABLE", "BORROWED", "UNAVAILABLE")

    suspend fun generateTestData(
        usersCount: Int = 20,
        booksCount: Int = 30,
        branchesCount: Int = 5,
        copiesPerBook: Int = 3
    ) = suspendTransaction {
        println("Генерация тестовых данных...")

        val branchIds = mutableListOf<Long>()
        repeat(branchesCount) { i ->
            val id = Branches.insert {
                it[name] = "Библиотека №${i + 1} им. ${faker.name().lastName()}"
                it[location] = faker.address().fullAddress()
                it[phone] = faker.phoneNumber().phoneNumber()
                it[worktime] = "Пн-Пт: 9:00-18:00, Сб: 10:00-16:00"
                it[createdTimestamp] = Clock.System.now()
            }[Branches.id]
            branchIds.add(id)
            println("Создан филиал: Библиотека №${i + 1}")
        }

        val userIds = mutableListOf<Long>()
        repeat(usersCount) { i ->
            val id = Users.insert {
                it[firstname] = faker.name().firstName()
                it[lastname] = faker.name().lastName()
                it[birthday] = faker.random().nextInt(1970, 2006).toString() +
                        "-${faker.random().nextInt(1, 12).toString().padStart(2, '0')}" +
                        "-${faker.random().nextInt(1, 28).toString().padStart(2, '0')}"
                it[phone] = faker.phoneNumber().phoneNumber()
                it[email] = "user${i + 1}@test.com"
                it[login] = "user${i + 1}"
                it[password] = PasswordHasher.hash("testpassword${i + 1}")
                it[status] = "ACTIVE"
                it[createdTimestamp] = Clock.System.now()
            }[Users.id]
            userIds.add(id)
        }
        println("Создано $usersCount пользователей")

        val bookIds = mutableListOf<Long>()
        repeat(booksCount) { _ ->
            val id = Books.insert {
                it[title] = faker.book().title()
                it[author] = faker.book().author()
                it[isbn] = "978-${Random.nextInt(1, 9)}-${Random.nextInt(1000, 9999)}-${Random.nextInt(1000, 9999)}-${Random.nextInt(0, 9)}"
                it[description] = faker.lorem().sentence()
                it[Books.genres] = genres_list.shuffled().take(Random.nextInt(1, 3)).joinToString(", ")
                it[availability] = true
                it[createdTimestamp] = Clock.System.now()
            }[Books.id]
            bookIds.add(id)
        }
        println("Создано $booksCount книг")

        var totalCopies = 0
        bookIds.forEach { bookId ->
            val copies = Random.nextInt(1, copiesPerBook + 1)
            repeat(copies) { copyIndex ->
                BooksStock.insert {
                    it[BooksStock.bookId] = bookId
                    it[branchId] = branchIds.random()
                    it[inventoryCode] = generateInventoryCode()
                    it[status] = statuses.random()
                    it[condition] = conditions.random()
                    it[notes] = if (Random.nextBoolean()) {
                        faker.lorem().sentence()
                    } else null
                    it[addedTimestamp] = Clock.System.now()
                    it[lastUpdateTimestamp] = Clock.System.now()
                }
                totalCopies++
            }
        }
        println("Создано $totalCopies экземпляров книг")
        println("Генерация тестовых данных завершена!")
    }
}
