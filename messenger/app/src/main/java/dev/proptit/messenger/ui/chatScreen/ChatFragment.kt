package dev.proptit.messenger.ui.chatScreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import dev.proptit.messenger.R
import dev.proptit.messenger.adapter.MessageAdapter
import dev.proptit.messenger.data.model.Message
import dev.proptit.messenger.databinding.FragmentChatBinding
import dev.proptit.messenger.ui.MainViewModel
import dev.proptit.messenger.setup.Keys
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels { MainViewModel.Factory }
    val adapter = MessageAdapter()

    private var state = EMOJI_STATE

    companion object {
        const val COMPOSE_STATE = 1
        const val EMOJI_STATE = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        onClickListener()
        observeData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //get arguments
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.receiverId.observe(viewLifecycleOwner, Observer { value ->
                    Log.d("test", "value: $value")
                    viewModel.getUserById(value).observe(viewLifecycleOwner, Observer { user ->
                        Glide
                            .with(requireContext())
                            .load(user.avatar)
                            .into(binding.ivAvatar)
                        binding.tvName.text = user.userName
                        adapter.setAvatar(user.avatar)
                        viewModel.allMessages.observe(viewLifecycleOwner, Observer { messages ->

                            adapter.setMessageList(messages.filter {
                                it.receiverId == Keys.MY_ID && it.senderId == user.id || it.receiverId == user.id && it.senderId == Keys.MY_ID
                            })
                        })
                    })


                })
            }
        }
    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        binding.etComposeMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.ivLike.setImageResource(R.drawable.ic_send)
                state = COMPOSE_STATE

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {}

        })

        binding.ivLike.setOnClickListener {
            if (state == COMPOSE_STATE) {
                viewModel.receiverId.value?.let { it1 ->
                    viewModel.senderId.value?.let { it2 ->
                        Message(
                            senderId = it2,
                            receiverId = it1,
                            message = binding.etComposeMessage.text.toString()
                        )
                    }
                }?.let { it2 ->
                    viewModel.insertMessage(
                        it2
                    )
                }
                binding.etComposeMessage.apply {
                    clearComposingText()
                    clearFocus()
                    text.clear()
                }
            }
        }
    }

    private fun initComponents() {
        binding.rvMessage.adapter = adapter


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}