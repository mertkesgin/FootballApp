package com.example.mertkesgin.footballapp.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.MatchesAdapter
import com.example.mertkesgin.footballapp.databinding.FragmentFavouriteMatchesBinding
import com.example.mertkesgin.footballapp.ui.matches.MatchesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteMatchesFragment : Fragment(R.layout.fragment_favourite_matches) {

    val matchesViewModel: MatchesViewModel by viewModels()

    private var _binding: FragmentFavouriteMatchesBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var matchesAdapter: MatchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteMatchesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFavouriteMatches()
    }

    private fun setupFavouriteMatches() {
        matchesViewModel.favouritesMatches.observe(viewLifecycleOwner, Observer {
            it?.let { matchesAdapter.differ.submitList(it.reversed()) }
        })
    }

    private fun setupRecyclerView() {
        binding.rvFavouriteMatches.apply {
            adapter = matchesAdapter
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        }
        setupRvItemClick()
    }

    private fun setupRvItemClick() {
        matchesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("match",it) }
            findNavController().navigate(R.id.action_favouritesFragment_to_matchDetailsFragment,bundle)
        }
    }
}