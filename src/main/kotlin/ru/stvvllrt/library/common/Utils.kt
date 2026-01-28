package ru.stvvllrt.library.common


fun generateInventoryCode(): String {
    val chars = ('A'..'Z') + ('0'..'9')
    val code = buildString(4) {
        repeat(4) {
            append(chars.random())
        }
    }
    return "INV-$code"
}
