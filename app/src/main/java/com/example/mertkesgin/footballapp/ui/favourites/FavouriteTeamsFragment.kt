package com.example.mertkesgin.footballapp.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.TeamsAdapter
import com.example.mertkesgin.footballapp.databinding.FragmentFavouriteTeamsBinding
import com.example.mertkesgin.footballapp.ui.teams.TeamsViewModel
import com.example.mertkesgin.footballapp.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteTeamsFragment : Fragment(R.layout.fragment_favourite_teams) {

    val teamsViewModel:TeamsViewModel by viewModels()

    private var _binding: FragmentFavouriteTeamsBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var teamsAdapter: TeamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteTeamsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFavouriteTeamsObserver()
    }

    private fun setupFavouriteTeamsObserver() {
        teamsViewModel.favouriteTeams.observe(viewLifecycleOwner, Observer {
            it?.let { teamsAdapter.differ.submitList(it) }
        })
    }

    private fun setupRecyclerView() {
        binding.rvFavouriteTeams.apply {
            adapter = teamsAdapter
            layoutManager = GridLayoutManager(activity, Constant.GRID_LAYOUT_COLUMN_COUNT, GridLayoutManager.VERTICAL,false)
        }
        setupRvClick()
    }

    private fun setupRvClick() {
        teamsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("team",it) }
            findNavController().navigate(R.id.action_favouritesFragment_to_teamDetailsFragment,bundle)
        }
    }
}