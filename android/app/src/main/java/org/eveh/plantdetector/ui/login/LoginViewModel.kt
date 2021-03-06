package org.eveh.plantdetector.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import org.eveh.plantdetector.R
import org.eveh.plantdetector.data.Repository
import org.eveh.plantdetector.data.model.LoggedInUser
import org.eveh.plantdetector.data.model.LoginFormState

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun login(username: String, password: String): LiveData<LoggedInUser>{
        val mutableData = MutableLiveData<LoggedInUser>()
        repository.loginFirebase(username, password).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank()
    }
}