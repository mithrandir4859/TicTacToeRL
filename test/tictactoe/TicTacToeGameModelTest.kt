package tictactoe

import org.junit.Assert.*

import org.junit.Test


class TicTacToeGameModelTest {

    val modelUnderTest = TicTacToeGameModel(gridSize = 3)

    @Test fun simple() {
        modelUnderTest.makeMove(0, 0)
        assertFalse(modelUnderTest.full)
        assertEquals(GameStatus.UNFINISHED, modelUnderTest.gameStatus)
    }

    @Test fun fastWin() {
        for (i in 0..1) {
            modelUnderTest.makeMove(0, i)
            modelUnderTest.makeMove(2, i)
        }
        modelUnderTest.makeMove(0, 2)

        assertFalse(modelUnderTest.full)
        assertEquals(GameStatus.SOMEONE_WON, modelUnderTest.gameStatus)
        assertEquals(Symbol.CROSS, modelUnderTest.winner)
    }

    @Test(expected = IllegalArgumentException::class) fun occupiedCell() {
        modelUnderTest.makeMove(0, 0)

        assertEquals(modelUnderTest[0][0], Symbol.CROSS);
        assertEquals(modelUnderTest.currentSymbol, Symbol.NOUGHT);

        modelUnderTest.makeMove(0, 0)
    }

    @Test fun diagonalWin() {
        modelUnderTest.makeMove(0, 0)
        modelUnderTest.makeMove(1, 0)
        modelUnderTest.makeMove(1, 1)
        modelUnderTest.makeMove(1, 2)
        modelUnderTest.makeMove(2, 2)

        assertEquals(GameStatus.SOMEONE_WON, modelUnderTest.gameStatus)
        assertEquals(Symbol.CROSS, modelUnderTest.winner)
    }

    @Test fun reverseDiagonalWin() {
        modelUnderTest.makeMove(1, 0)
        modelUnderTest.makeMove(0, 2)

        modelUnderTest.makeMove(0, 1)
        modelUnderTest.makeMove(1, 1)

        modelUnderTest.makeMove(2, 2)
        modelUnderTest.makeMove(2, 0)

        assertEquals(GameStatus.SOMEONE_WON, modelUnderTest.gameStatus)
        assertEquals(Symbol.NOUGHT, modelUnderTest.winner)
        assertFalse(modelUnderTest.full)
    }

    @Test fun reverseDiagonalWinFullBoard(){
        modelUnderTest.makeMove(2, 2)
        modelUnderTest.makeMove(0, 0)

        modelUnderTest.makeMove(1, 0)
        modelUnderTest.makeMove(0, 1)

        modelUnderTest.makeMove(0, 2)
        modelUnderTest.makeMove(2, 1)

        modelUnderTest.makeMove(2, 0)
        modelUnderTest.makeMove(1, 2)

        modelUnderTest.makeMove(1, 1)

        assertTrue(modelUnderTest.full)
        assertEquals(GameStatus.SOMEONE_WON, modelUnderTest.gameStatus)
        assertEquals(Symbol.CROSS, modelUnderTest.winner)
    }

    @Test(expected = IllegalStateException::class) fun draw(){
        modelUnderTest.makeMove(0, 0)
        modelUnderTest.makeMove(0, 1)

        modelUnderTest.makeMove(0, 2)
        modelUnderTest.makeMove(1, 0)

        modelUnderTest.makeMove(1, 1)
        modelUnderTest.makeMove(2, 2)

        modelUnderTest.makeMove(1, 2)
        modelUnderTest.makeMove(2, 0)

        modelUnderTest.makeMove(2, 1)

        assertTrue(modelUnderTest.full)
        assertEquals(GameStatus.DRAW, modelUnderTest.gameStatus)
        modelUnderTest.winner
    }


}
