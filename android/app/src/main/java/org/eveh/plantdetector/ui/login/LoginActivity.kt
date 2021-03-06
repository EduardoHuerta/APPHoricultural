package org.eveh.plantdetector.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import org.eveh.plantdetector.R
import org.eveh.plantdetector.data.Constants
import org.eveh.plantdetector.data.ViewModelFactory
import org.eveh.plantdetector.ui.main_menu.MenuActivity
import org.eveh.plantdetector.util.afterTextChanged
import org.eveh.plantdetector.util.toastShort

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProvider(this, ViewModelFactory())
                .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                username.text.toString(),
                                password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString()).observe(this@LoginActivity, Observer {
                    loading.visibility = View.GONE
                    if (!it.success){
                     toastShort(it.message)
                     return@Observer
                    }
                    onSuccessLogin()
                })
            }
        }
    }

    private fun onSuccessLogin(){
        val sharedPref = getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(Constants.UserData.SESSION_STATUS_KEY, true)
            apply()
        }
        startActivity(Intent(this, MenuActivity::class.java))
    }
}

