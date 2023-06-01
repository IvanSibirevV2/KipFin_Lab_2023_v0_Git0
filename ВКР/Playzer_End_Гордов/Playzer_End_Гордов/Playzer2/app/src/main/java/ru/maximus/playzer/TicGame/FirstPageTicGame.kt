package ru.maximus.playzer.TicGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ru.maximus.playzer.R

class FirstPageTicGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page_tic_game)

        var button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            val intent = Intent (this, TicTacTaoGame::class.java)
            startActivityForResult(intent,0)
        }
    }
}