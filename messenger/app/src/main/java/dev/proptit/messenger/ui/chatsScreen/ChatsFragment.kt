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
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.databinding.FragmentChatsBinding
import dev.proptit.messenger.ui.MainViewModel
import dev.proptit.messenger.setup.Keys

class ChatsFragment : Fragment() {
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels { MainViewModel.Factory }


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
        val storyAdapter = PersonAdapter(onItemClick = {onItemClick(it)})
        val chatsAdapter = PersonAdapter(onItemClick = {onItemClick(it)})
        var userList = listOf<User>()
        viewModel.getAllUser().observe(viewLifecycleOwner, Observer { users ->
            users.let {
                userList = users.filter {
                    (it.id != Keys.MY_ID)
                }
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
        viewModel.setSenderId(Keys.MY_ID)
        findNavController().navigate(R.id.action_chatsFragment_to_chatFragment2)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}