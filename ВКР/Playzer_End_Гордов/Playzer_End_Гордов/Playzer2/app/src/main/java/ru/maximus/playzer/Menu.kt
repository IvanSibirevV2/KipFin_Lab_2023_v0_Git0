package ru.maximus.playzer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.maximus.playzer.SnakeGame.FirstPSnake
import ru.maximus.playzer.TicGame.FirstPageTicGame
import ru.maximus.playzer.trickmath.FirstPtrickmath

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val TicTac = findViewById<Button>(R.id.button)
        TicTac.setOnClickListener {
            val intent = Intent (this, FirstPageTicGame::class.java)
            startActivityForResult(intent,0)
        }

        val Snake = findViewById<Button>(R.id.Snake)
        Snake.setOnClickListener{
            val intent = Intent (this, FirstPSnake::class.java)
            startActivityForResult(intent,0)
        }

        var nameView = findViewById<TextView>(R.id.nameView)
        val firebase = FirebaseAuthentication.get()
        firebase.userResult.observe(this,
            Observer {
                it?.let {
                    nameView.text = it.name
                }
            })

        if(FirebaseAuth.getInstance().currentUser != null){lifecycleScope.launch(Dispatchers.IO) { firebase.usernam() }}

        val signout = findViewById<Button>(R.id.signout)
        signout.setOnClickListener {
            firebase.signOut()
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        /*
        val trickmath = findViewById<Button>(R.id.trickmath)
        trickmath.setOnClickListener { val intent = Intent (this, FirstPtrickmath::class.java)
            startActivityForResult(intent,0)
        }
            <Button
        android:id="@+id/trickmath"
        android:layout_width="304dp"
        android:layout_height="48dp"
        android:layout_marginTop="24dp"
        android:text="Матиматика с подвохом"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.495"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/Snake" />
         */
    }
}