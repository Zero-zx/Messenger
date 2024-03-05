package dev.proptit.messenger.ui.peopleScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dev.proptit.messenger.adapter.PeopleAdapter
import dev.proptit.messenger.databinding.FragmentPeopleBinding
import dev.proptit.messenger.ui.MainViewModel

class PeopleFragment : Fragment() {

    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels { MainViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allUser.observe(viewLifecycleOwner, Observer {
            binding.rvPeople.adapter = PeopleAdapter(
                it
            )
        })
    }

}