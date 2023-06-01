package ru.maximus.playzer

import android.app.Application
import com.google.firebase.FirebaseApp

class Playzer : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseAuthentication.initialize()
    }
}