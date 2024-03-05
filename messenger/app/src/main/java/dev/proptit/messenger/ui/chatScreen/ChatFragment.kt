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
import dev.proptit.messenger.setup.PrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager
    private val viewModel: MainViewModel by activityViewModels { MainViewModel.Factory }
    private lateinit var adapter: MessageAdapter
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
                    viewModel.getUserById(value).observe(viewLifecycleOwner, Observer { user ->
                        Glide
                            .with(requireContext())
                            .load(user.avatarUri)
                            .into(binding.ivAvatar)
                        binding.tvName.text = user.name
                        adapter.setAvatar(user.avatarUri)
                    })



                        viewModel.getMessageByContactId(value,prefManager.get(Keys.MY_ID,-1)).observe(viewLifecycleOwner, Observer {
                            adapter.setMessageList(it)
                        })
//                        adapter.setMessageList(viewModel.getMessageByContactId(value, prefManager.get(Keys.MY_ID,-1)))
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

                val receiverID = viewModel.receiverId.value
                val senderID = viewModel.senderId.value

                if(receiverID != null && senderID != null){
                    val message = Message(
                        senderId = senderID,
                        receiverId = receiverID,
                        message = binding.etComposeMessage.text.toString(),
                        time = System.currentTimeMillis()
                    )
                    viewModel.sendMessage(message)
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
        viewModel.fetchDataFromRemote()
        prefManager = PrefManager(requireContext())
        adapter = MessageAdapter(prefManager.get(Keys.MY_ID, -1))
        binding.rvMessage.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}