package dev.proptit.messenger.ui.loadingScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.proptit.messenger.ui.MainActivity
import dev.proptit.messenger.databinding.ActivitySplashBinding
import dev.proptit.messenger.ui.loginScreen.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
//    private val userRepository = UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        userRepository.readUsers()

        binding.llSplash.apply {
            alpha = 0f
            animate().setDuration(2000).alpha(1f).withEndAction{
                val intent= Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }




    }
}