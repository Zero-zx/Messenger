package dev.proptit.messenger.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.data.model.Message
import dev.proptit.messenger.databinding.ReceivedMessageItemBinding
import dev.proptit.messenger.databinding.SentMessageItemBinding
import dev.proptit.messenger.setup.Keys

class MessageAdapter(
    private val myId: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var messageList = listOf<Message>()
    private var avatar: String = ""
    companion object {
        const val SENT_MASSAGE = 1
        const val RECEIVED_MASSAGE = 2
    }

    inner class ReceivedMessageViewHolder(private val binding: ReceivedMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message, avatar: String) {
            binding.tvMessage.text = message.message
            Glide.with(itemView.context).load(avatar).centerCrop().into(binding.ivAvatar)
        }
    }

    inner class SentMessageViewHolder(private val binding: SentMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.tvMessage.text = message.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            SENT_MASSAGE -> SentMessageViewHolder(
                SentMessageItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )

            RECEIVED_MASSAGE -> ReceivedMessageViewHolder(
                ReceivedMessageItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException(viewType.toString())
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder.itemViewType) {
            SENT_MASSAGE -> (holder as SentMessageViewHolder).bind(messageList[position])
            RECEIVED_MASSAGE -> (holder as ReceivedMessageViewHolder).bind(
                messageList[position],
                avatar
            )

            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        Log.d("Message", message.toString())
        return if (message.senderId == myId) SENT_MASSAGE
        else RECEIVED_MASSAGE
    }

    fun setMessageList(messageList: List<Message>){
        this@MessageAdapter.messageList = messageList
        notifyDataSetChanged()
    }

    fun setAvatar(avatar: String){
        this@MessageAdapter.avatar = avatar
    }
}