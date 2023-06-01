package ru.maximus.playzer.TicGame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ru.maximus.playzer.R

class TicTacTaoGame : AppCompatActivity() {

    private lateinit var game: TicTacLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_tao_game)

        game = TicTacLogic(this)

        // Set click listeners for the game board buttons
        val buttons = Array(3) { arrayOfNulls<Button>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonId = resources.getIdentifier("button_$i$j", "id", packageName)
                buttons[i][j] = findViewById(buttonId)
                buttons[i][j]?.setOnClickListener {
                    if (!game.isGameEnded() && game.makeMove(i, j)) {
                        updateBoard(buttons)
                        //game.checkForWin()
                    }
                }
            }
        }

        // Set click listener for the "New Game" button
        val newGameButton = findViewById<Button>(R.id.new_game_button)
        newGameButton.setOnClickListener {
            game.resetGame()
            updateBoard(buttons)
        }
    }

    private fun updateBoard(buttons: Array<Array<Button?>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]?.text = game.board[i][j]
            }
        }
    }
}