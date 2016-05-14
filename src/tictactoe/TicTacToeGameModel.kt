package tictactoe

import util.Indexable
import java.util.*


class TicTacToeGameModel(val gridSize: Int = 3) : Indexable<Indexable<Symbol?>> {

    private val gameBoard: Array<Array<Symbol?>> = Array<Array<Symbol?>>(gridSize) { Array(gridSize) { null } }
    private val gridSizeMinusOne = gridSize - 1

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

    private val full: Boolean
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
        val winner = getWinner(row, column)
        updateModelState(winner)
    }

    private fun updateModelState(winner: Symbol?) {
        if (winner != null) {
            _winner = winner
            _gameStatus = GameStatus.SOMEONE_WON
        } else if (full) {
            _gameStatus = GameStatus.DRAW
        } else {
            _currentSymbol = currentSymbol.other
        }
    }

    private fun getRow(row: Int) = gameBoard[row].toList()

    private fun getColumn(column: Int) = (0..gridSizeMinusOne).map { row -> gameBoard[row][column] }.toList()

    private val mainDiagonal: List<Symbol?>
        get() = (0..gridSizeMinusOne).map { i -> gameBoard[i][i] }.toList()

    private val reverseDiagonal: List<Symbol?>
        get() = (0..gridSizeMinusOne).map { i -> gameBoard[i][gridSizeMinusOne - i] }.toList()

    private fun getWinner(row: Int, column: Int): Symbol? {
        val symbolSequencesToCheck = hashSetOf(
                getColumn(column), getRow(row)
        )
        if (row == column) {
            symbolSequencesToCheck.add(mainDiagonal)
        }
        if (row + column == gridSizeMinusOne) {
            symbolSequencesToCheck.add(reverseDiagonal)
        }
        val potentialWinners = symbolSequencesToCheck.map {
            symbols -> if (symbols.toHashSet().size == 1) symbols.iterator().next() else null
        }.toHashSet()
        potentialWinners.remove(null)
        if (potentialWinners.isEmpty()) {
            return null
        }
        if (potentialWinners.size == 1) {
            return potentialWinners.iterator().next()
        }
        throw AssertionError("Logic failed")
    }

    override fun get(i: Int): Indexable<Symbol?> =
            object : Indexable<Symbol?> {
                override fun get(j: Int) = gameBoard[i][j]
            }

}

