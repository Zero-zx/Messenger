package dev.proptit.messenger.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.databinding.ChatItemBinding
import dev.proptit.messenger.databinding.StoryItemBinding

class PersonAdapter(
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var viewType = 1
    private var userList = listOf<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            1 -> StoryViewHolder(StoryItemBinding.inflate(layoutInflater, parent, false))
            2 -> ChatViewHolder(
                ChatItemBinding.inflate(layoutInflater, parent, false),
                onItemClick
            )

            else -> throw IllegalArgumentException(viewType.toString())
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("test", viewType.toString())
        return when (viewType) {
            1 -> (holder as StoryViewHolder).bind(userList[position])
            2 -> (holder as ChatViewHolder).bind(userList[position])
            else -> throw IllegalArgumentException("Invalid item type")
        }


    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    fun setViewType(type: Int){
        viewType = type;
    }

    fun setUserList(list: List<User>) {
        userList = list
        notifyDataSetChanged()
    }
}