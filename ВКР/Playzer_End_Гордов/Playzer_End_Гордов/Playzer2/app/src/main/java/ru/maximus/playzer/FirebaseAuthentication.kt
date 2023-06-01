package ru.maximus.playzer

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseAuthentication  {
    val signInResult = MutableLiveData<Boolean?>()
    val scoreResult = MutableLiveData<Long?>()
    val usersResult = MutableLiveData<List<User>>()
    val userResult = MutableLiveData<User>()
    var userList: MutableList<User> = mutableListOf()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    // Реализация функции для регистрации нового пользователя по электронной почте и паролю
    suspend fun signUp(email: String, password: String, name: String): Boolean {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (authResult.user != null) {
                db.collection("players")
                    .document(authResult.user!!.uid)
                    .set(hashMapOf(
                        "name" to name
                    ))
                    .await()
                firebaseAuth.signOut()
            }
            authResult.user != null
        } catch (e: Exception) {
            return false
        }
    }


    // Реализация функции для входа существующего пользователя по электронной почте и паролю
    suspend fun signIn(email: String, password: String): Boolean {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            signInResult.postValue(authResult.user != null)
            authResult.user != null
        } catch (e: Exception) {
            return false
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getSignedInUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    suspend fun readSnakeBestScore() {
        val user = firebaseAuth.currentUser!!
        val result = db.collection("players")
            .document(user.uid)
            .get()
            .addOnSuccessListener {
                val data = it.data
                if (data != null) {
                    val score = data["snakeScore"] as Long?
                    scoreResult.postValue(score)
                }

            }
            .await()
    }

    suspend fun username(){
    }

    suspend fun getUsers() {
        val users = db.collection("players").orderBy("snakeScore", Query.Direction.DESCENDING).get().await()
        userList = mutableListOf()
        for (user in users) {
            userList.add(User(
                user.getString("name")!!,
                user.getLong("snakeScore")
            ))
        }
        usersResult.postValue(userList)
    }

    suspend fun usernam(){
        var postDocument = db.collection("players").document(firebaseAuth.currentUser!!.uid).get().await()
        var user = User(
            postDocument.getString("name")!!,
            postDocument.get("snakeScore", Long::class.java)
        )
        userResult.postValue(user)
    }

    suspend fun writeSnakeBestScore(bestScore: Long) {
        val user = firebaseAuth.currentUser!!
        val userMap: Map<String, Long> = hashMapOf(
                "snakeScore" to bestScore
        )
        db.collection("players")
            .document(user.uid)
            .set(userMap, SetOptions.merge())
            .await()
    }

    companion object {
        private var INSTANCE: FirebaseAuthentication? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = FirebaseAuthentication()
            }
        }

        fun get(): FirebaseAuthentication {
            return INSTANCE ?:
            throw IllegalStateException("FirebaseAuthentication must be initialized")
        }
    }
}