package dev.proptit.messenger.ui.chatsScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dev.proptit.messenger.R
import dev.proptit.messenger.adapter.PersonAdapter
import dev.proptit.messenger.data.dto.UserOutputDto
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.data.remote.ApiClient
import dev.proptit.messenger.databinding.FragmentChatsBinding
import dev.proptit.messenger.ui.MainViewModel
import dev.proptit.messenger.setup.Keys
import dev.proptit.messenger.setup.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatsFragment : Fragment() {
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels { MainViewModel.Factory }
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()

    }


    private fun initComponents() {
        prefManager = PrefManager(requireContext())
        val storyAdapter = PersonAdapter(onItemClick = {onItemClick(it)})
        val chatsAdapter = PersonAdapter(onItemClick = {onItemClick(it)})
        var userList = listOf<User>()
        viewModel.allUser.observe(viewLifecycleOwner, Observer { users ->
            users.let {
                userList = users.filter {
                    (it.id != prefManager.get(Keys.MY_ID, -1))
                }
//                binding.tvTitle.text = userList.toString()
                storyAdapter.setUserList(userList)
                chatsAdapter.setUserList(userList)
                Log.d("test", userList.toString())

            }
        })
        binding.rvStory.adapter = storyAdapter
        binding.rvChat.adapter = chatsAdapter
        chatsAdapter.setViewType(2)

    }

    private fun onItemClick(user: User) {
        viewModel.setReceiverId(user.id)
        viewModel.setSenderId(prefManager.get(Keys.MY_ID, -1))
        findNavController().navigate(R.id.action_chatsFragment_to_chatFragment2)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}