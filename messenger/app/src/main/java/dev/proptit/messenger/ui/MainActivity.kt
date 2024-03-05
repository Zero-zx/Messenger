package dev.proptit.messenger.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import dev.proptit.messenger.R
import dev.proptit.messenger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        viewModel.insertUser(User(userName = "Admin", avatar = "https://firebasestorage.googleapis.com/v0/b/messenger-z001.appspot.com/o/avatars%2Fimg_avatar.png?alt=media&token=6c1b7bc7-d5ac-437a-8b26-7deddf6c9b6f"))
//        viewModel.insertUser(User(userName = "Hieu Trung", avatar = "https://firebasestorage.googleapis.com/v0/b/messenger-z001.appspot.com/o/avatars%2Fimg_avatar.png?alt=media&token=6c1b7bc7-d5ac-437a-8b26-7deddf6c9b6f"))
//        viewModel.insertUser(User(userName = "Mark Zuckerberg", avatar = "https://firebasestorage.googleapis.com/v0/b/messenger-z001.appspot.com/o/avatars%2Fimg_avatar2.png?alt=media&token=e4ee088d-fb34-45d5-82bf-0b6baefa6e94"))
//        viewModel.insertMessage(Message(senderId = 0, receiverId = 1, message =  "How are you"))
//        viewModel.insertMessage(Message(senderId = 1, receiverId = 0, message =  "I'm successful"))
//        viewModel.insertMessage(Message(senderId = 2, receiverId = 1, message =  "I'm hungry"))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomMenu, navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.chatFragment) {
                binding.bottomMenu.visibility = View.GONE
            } else {
                binding.bottomMenu.visibility = View.VISIBLE
            }
        }

    }
}