package tictactoe

enum class Symbol {
    CROSS, NOUGHT;

    val other: Symbol
        get() = if (this == CROSS) NOUGHT else CROSS
}
