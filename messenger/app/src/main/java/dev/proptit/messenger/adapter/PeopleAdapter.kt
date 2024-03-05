package dev.proptit.messenger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.databinding.PeopleItemBinding

class PeopleAdapter(
    private val peopleList: List<User>
): RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    inner class PeopleViewHolder(private val binding: PeopleItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(person: User){
            Glide.with(itemView.context).load(person.avatar).centerCrop().into(binding.ivAvatar)
            binding.tvName.text = person.userName
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PeopleViewHolder(PeopleItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(peopleList[position])
    }

}