package dev.proptit.messenger.ui.loginScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import dev.proptit.messenger.R
import dev.proptit.messenger.data.remote.ApiClient
import dev.proptit.messenger.data.remote.UserService
import dev.proptit.messenger.databinding.ActivityLoginBinding
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.setup.PrefManager
import dev.proptit.messenger.ui.MainActivity
import dev.proptit.messenger.ui.registerScreen.RegisterActivity
import dev.proptit.messenger.ui.screen.login.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels{
        LoginViewModel.Factory(ApiClient.userService)
    }
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager(this)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnCreateNewAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            loginViewModel.login(
                username,
                password,
                onSuccess = {
                    prefManager.put(Keys.MY_ID, it)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                onError = {
                    Toast.makeText(this, "Username or password is incorrect", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }


}