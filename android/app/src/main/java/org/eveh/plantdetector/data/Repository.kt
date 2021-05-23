package org.eveh.plantdetector.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.eveh.plantdetector.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class Repository(val dataSource: DataSource) {

    private lateinit var database: DatabaseReference

    fun loginFirebase(username: String, password: String): LiveData<LoggedInUser>{
        var loggedInUser = MutableLiveData<LoggedInUser>()

        database = Firebase.database.reference
        database.child("login").child(username).child("password").get().addOnSuccessListener {
            Log.d("LOGIN", "login: ${it.value}")
            when {
                it.value == null -> {
                    loggedInUser.value = LoggedInUser(false,"El usuario no existe.")
                }
                it.value.toString() != password -> {
                    loggedInUser.value = LoggedInUser(false,"Contraseña incorrecta.")
                }
                else -> loggedInUser.value = LoggedInUser(true,"Inicio de sesión exitoso.")
            }
        }
        return loggedInUser
    }
}