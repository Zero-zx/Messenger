package dev.proptit.messenger.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.databinding.StoryItemBinding

class StoryViewHolder(private val binding: StoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.tvName.text = user.name
        Glide.with(itemView.context)
            .load(user.avatarUri)
            .centerCrop()
            .into(binding.ivStory)
    }
}
