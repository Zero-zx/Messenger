package dev.proptit.messenger.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.databinding.StoryItemBinding

class StoryViewHolder(private val binding: StoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.tvName.text = user.userName
        Glide.with(itemView.context)
            .load(user.avatar)
            .centerCrop()
            .into(binding.ivStory)
    }
}
