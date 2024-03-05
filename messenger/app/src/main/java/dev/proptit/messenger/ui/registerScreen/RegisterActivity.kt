package dev.proptit.messenger.ui.registerScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import dev.proptit.messenger.R
import dev.proptit.messenger.data.remote.ApiClient
import dev.proptit.messenger.databinding.ActivityRegisterBinding
import dev.proptit.messenger.ui.screen.login.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterViewModel.Factory(ApiClient.userService)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnCreate.setOnClickListener {
            val name = binding.etName.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            registerViewModel.register(
                name,
                username,
                password,
                {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    finish()
                },
                {
                    Toast.makeText(this, "This username has already been taken ", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}