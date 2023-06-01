package ru.maximus.playzer.TicGame

import android.content.Context
import android.widget.Toast

class TicTacLogic(private val context: Context) {
    val board = Array(3) { Array(3) { "" } }
    var currentPlayer = "X"
    var gameEnded = false


    fun makeMove(row: Int, col: Int): Boolean {
        if (board[row][col] == "" && !gameEnded) {
            board[row][col] = currentPlayer
            checkForWin()
            currentPlayer = if (currentPlayer == "X") "O" else "X"

            return true
        }
        return false
    }
    fun isGameEnded(): Boolean {
        return gameEnded
    }


    fun checkForWin() {
        // Check rows
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != "") {
                gameEnded = true
                showToastWin()
                // Display win message and prompt for new game
                return
            }
        }

        // Check columns
        for (j in 0..2) {
            if (board[0][j] == board[1][j] && board[0][j] == board[2][j] && board[0][j] != "") {
                gameEnded = true
                showToastWin()
                // Display win message and prompt for new game
                return
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != "") {
            gameEnded = true
            showToastWin()
            // Display win message and prompt for new game
            return
        }
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != "") {
            gameEnded = true
            showToastWin()
// Display win message and prompt for new game
            return
        }
        // Check for tie
        if (isBoardFull() && !gameEnded) {
            gameEnded = true
            showToastWin()
            // Display tie message and prompt for new game
        }

    }

    fun showToastWin(){
        val message = "Игрок $currentPlayer выйграл!"
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == "") {
                    return false
                }
            }
        }
        return true
    }

    fun resetGame() {
        board.forEach { it.fill("") }
        currentPlayer = "X"
        gameEnded = false
    }
}