package tictactoe

import util.Indexable
import java.util.HashSet


class TicTacToeGameModel(val gridSize: Int = 3) : Indexable<Indexable<Symbol?>> {

    private val gameBoard: Array<Array<Symbol?>> = Array<Array<Symbol?>>(gridSize) { Array(gridSize) { null } }
    val gridSizeMinusOne = gridSize - 1

    private var _currentSymbol: Symbol = Symbol.CROSS
    private var _winner: Symbol = Symbol.CROSS
    private var _gameStatus: GameStatus = GameStatus.UNFINISHED

    val currentSymbol: Symbol
        get() {
            if (gameStatus == GameStatus.UNFINISHED) {
                return _currentSymbol
            }
            throw IllegalStateException("Game is finished")
        }

    val gameStatus: GameStatus
        get() = _gameStatus

    val winner: Symbol
        get() {
            if (gameStatus == GameStatus.SOMEONE_WON)
                return _winner
            throw IllegalStateException("No winner")
        }

    val full: Boolean
        get() {
            for (i in 0..gridSizeMinusOne)
                for (j in 0..gridSizeMinusOne)
                    if (gameBoard[i][j] == null) return false
            return true
        }

    fun makeMove(row: Int, column: Int) {
        val boardSymbol = gameBoard[row][column]
        if (boardSymbol != null) {
            throw IllegalArgumentException("Cell ($row, $column) is already occupied with $boardSymbol")
        }
        gameBoard[row][column] = currentSymbol
        val potentialWinners = hashSetOf(
                getRowWinner(row),
                getColumnWinner(column),
                getDiagonalWinner(row, column)
        )
        potentialWinners.remove(null)
        if (potentialWinners.size > 1) throw AssertionError("Logic failed")
        if (potentialWinners.size == 1) {
            _winner = potentialWinners.iterator().next()!!
            _gameStatus = GameStatus.SOMEONE_WON
            return
        }
        if (full){
            _gameStatus = GameStatus.DRAW
        } else {
            _currentSymbol = currentSymbol.other
        }
    }

    private fun getWinner(symbols: Collection<Symbol?>): Symbol? {
        return if (symbols.size == 1) symbols.iterator().next() else null
    }

    private fun getDiagonalWinner(row: Int, column: Int): Symbol? {
        val symbols = HashSet<Symbol?>()
        if (row == column) {
            for (i in 0..gridSizeMinusOne) {
                symbols.add(gameBoard[i][i])
            }
        } else if (row + column == gridSizeMinusOne) {
            for (i in 0..gridSizeMinusOne) {
                symbols.add(gameBoard[i][gridSizeMinusOne - i])
            }
        }
        return getWinner(symbols)
    }

    private fun getRowWinner(row: Int): Symbol? = getWinner(gameBoard[row].toHashSet())

    private fun getColumnWinner(column: Int): Symbol? {
        val columnValues = HashSet<Symbol?>()
        for (row in 0..gridSizeMinusOne) {
            columnValues.add(gameBoard[row][column])
        }
        return getWinner(columnValues)
    }

    override fun get(i: Int): Indexable<Symbol?> =
            object : Indexable<Symbol?> {
                override fun get(j: Int) = gameBoard[i][j]
            }

}

