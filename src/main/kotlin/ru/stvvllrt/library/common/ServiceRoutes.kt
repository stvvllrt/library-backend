package ru.stvvllrt.library.common

import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.jetbrains.exposed.v1.core.exposedLogger

fun Route.serviceRoutes() {
    route("api/v1/services") {
        route("/generateData"){
            post{
                try{
                    exposedLogger.info("Generating data...")
                    TestDataGenerator.generateTestData()
                    exposedLogger.info("Data generation completed.")
                } catch (e: Exception) {
                    exposedLogger.error("Error during data generation: ${e.stackTraceToString()}")

                }
            }
        }
    }

}