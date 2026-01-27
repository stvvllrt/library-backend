package ru.stvvllrt.library

import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain
import ru.stvvllrt.library.plugins.configureDatabases
import ru.stvvllrt.library.plugins.configureRouting
import ru.stvvllrt.library.plugins.configureSecurity

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureRouting()
    configureDatabases()
}
