package ru.maximus.playzer.SnakeGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.maximus.playzer.Adaptr
import ru.maximus.playzer.FirebaseAuthentication
import ru.maximus.playzer.R
import ru.maximus.playzer.User

class FirstPSnake : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adaptr
    private lateinit var firebase: FirebaseAuthentication

    fun getTopPlayers(): List<User> {
        firebase = FirebaseAuthentication.get()
        return (firebase.userList.map { User(it.name, it.snakeScore) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_psnake)

        firebase = FirebaseAuthentication.get()

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        firebase.usersResult.observe(
            this,
            Observer { result ->
                adapter = Adaptr(result)
                recyclerView.adapter = adapter
                updateUI()
            }
        )
        lifecycleScope.launch(Dispatchers.IO) {
            firebase.getUsers()
        }

        var butten = findViewById<Button>(R.id.Play)
        butten.setOnClickListener{
            val intent = Intent (this, SnakeGa::class.java)
            startActivityForResult(intent,0)
        }
    }

    private fun updateUI() {
        recyclerView.adapter!!.notifyDataSetChanged()
    }
}