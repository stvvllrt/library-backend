package ru.stvvllrt

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.stvvllrt.ru.stvvllrt.library.module
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}
