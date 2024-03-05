package dev.proptit.messenger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.databinding.DiscoverItemBinding

class DiscoverAdapter(
    private val context: Context,
    private val discoverList: List<User>
) : RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder>() {

    inner class DiscoverViewHolder(private val binding: DiscoverItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: User) {
            Glide.with(context).load(person.avatar).centerCrop().into(binding.ivAvatar)
            binding.tvName.text = person.userName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DiscoverViewHolder(DiscoverItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return discoverList.size
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        holder.bind(discoverList[position])
    }

}