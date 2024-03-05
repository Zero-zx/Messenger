package dev.proptit.messenger.ui.discoverScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dev.proptit.messenger.data.model.Post
import dev.proptit.messenger.data.remote.ApiClient
import dev.proptit.messenger.databinding.FragmentDiscoverBinding
import dev.proptit.messenger.ui.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels { MainViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllUser().observe(viewLifecycleOwner, Observer {
//            binding.rvDiscover.adapter = DiscoverAdapter(
//                requireContext(),
//                it
//            )
        })

        binding.ivAvatar.setOnClickListener {
            val postId = 4
            val call = ApiClient.apiService.getPostById(postId)

            call.enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        val post = response.body()
                        Log.d("test", post.toString())
                        if (post != null) {
                            binding.tvTitle.text = post.title
                        }
                        if (post != null) {
                            binding.tvBody.text = post.body
                        }
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.d("test", "can't call")
                }

            })
        }
    }
}