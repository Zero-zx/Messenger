package dev.proptit.messenger.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.databinding.ChatItemBinding

class ChatViewHolder(
    private val binding: ChatItemBinding,
    private val onItemClick: (User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(person: User) {
        binding.tvName.text = person.name
        Glide.with(itemView.context)
            .load(person.avatarUri)
            .centerCrop()
            .into(binding.ivAvatar)


        binding.root.setOnClickListener {
            onItemClick(person)
        }
    }
}
