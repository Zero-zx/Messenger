package dev.proptit.messenger.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.databinding.ChatItemBinding
import kotlinx.coroutines.withContext

class ChatViewHolder(
    private val binding: ChatItemBinding,
    private val onItemClick: (User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(person: User) {
        binding.tvName.text = person.userName
        Glide.with(itemView.context)
            .load(person.avatar)
            .centerCrop()
            .into(binding.ivAvatar)


        binding.root.setOnClickListener {
            onItemClick(person)
        }
    }
}
