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

class Auth : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuthentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        FirebaseAuthentication.initialize()
        firebaseAuth = FirebaseAuthentication.get()

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

        val username = findViewById<EditText>(R.id.edit_user)
        val mail = findViewById<EditText>(R.id.edit_mail)
        val password = findViewById<EditText>(R.id.edit_password)
        val repassword = findViewById<EditText>(R.id.repasword)
        val loginButton = findViewById<Button>(R.id.button_auth)

        loginButton.setOnClickListener{
            if (password.text.count() >= 6){
                if(password.text.toString() == repassword.text.toString()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        firebaseAuth.signUp(mail.text.toString(), password.text.toString(), username.text.toString())
                            updateUI()
                    }
                }
                else{Toast.makeText(this, "Пароли не совподают", Toast.LENGTH_LONG).show()}
            }
            else{Toast.makeText(this, "Пароль меньше чем 6 символов", Toast.LENGTH_LONG).show()}

        }


    }

    private fun showToastFail() {
        Toast.makeText(this, "Проверьте свою почту", Toast.LENGTH_LONG).show()
    }

    fun updateUI() {
        finish()
    }

}