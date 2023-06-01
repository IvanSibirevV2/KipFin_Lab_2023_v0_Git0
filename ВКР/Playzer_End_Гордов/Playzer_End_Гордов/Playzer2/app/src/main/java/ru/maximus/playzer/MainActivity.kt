package ru.maximus.playzer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuthentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuthentication.get()
        if (firebaseAuth.getSignedInUser() != null) {
            updateUI()
        }
        firebaseAuth.signInResult.observe(this,
            Observer {
                it?.let {
                    if (it) {
                        updateUI()
                    } else {
                        showToastFail()
                    }
                }
            })
        val reg_but = findViewById<Button>(R.id.button_reg)
        reg_but.setOnClickListener {
            val intent = Intent(this, Auth::class.java)
            startActivityForResult(intent,0)
        }
        val username = findViewById<EditText>(R.id.edit_user)
        val password = findViewById<EditText>(R.id.edit_password)
        val loginButton = findViewById<Button>(R.id.button_login)
        loginButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                firebaseAuth.signIn(username.text.toString(), password.text.toString())
            }
        }
    }
    private fun showToastFail() {
        Toast.makeText(this, "Не верный логин или пароль", Toast.LENGTH_LONG).show()
    }
    fun updateUI() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
        finish()
    }
}