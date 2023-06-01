package ru.maximus.playzer.trickmath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ru.maximus.playzer.R
import ru.maximus.playzer.TicGame.TicTacTaoGame

class FirstPtrickmath : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_ptrickmath)

        var button = findViewById<Button>(R.id.playtrm)
        button.setOnClickListener {
            val intent = Intent(this, TrickMath::class.java)
            startActivityForResult(intent, 0)
        }
    }
}