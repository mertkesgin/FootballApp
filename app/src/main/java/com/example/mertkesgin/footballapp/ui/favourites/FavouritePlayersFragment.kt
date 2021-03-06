package com.example.mertkesgin.footballapp.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.PlayerAdapter
import com.example.mertkesgin.footballapp.base.BaseFragment
import com.example.mertkesgin.footballapp.databinding.FragmentFavouritePlayersBinding
import com.example.mertkesgin.footballapp.ui.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouritePlayersFragment : BaseFragment<FragmentFavouritePlayersBinding>() {

    private val playerViewModel:PlayerViewModel by viewModels()

    @Inject
    lateinit var playerAdapter: PlayerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFavouritePlayerObserver()
    }

    private fun setupFavouritePlayerObserver() {
        playerViewModel.favouritePlayers.observe(viewLifecycleOwner, Observer {
            it?.let { playerAdapter.differ.submitList(it) }
        })
    }

    private fun setupRecyclerView() {
        binding.rvFavouritePlayers.apply {
            adapter = playerAdapter
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        }
        setupRvItemClick()
    }

    private fun setupRvItemClick() {
        playerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("player",it) }
            findNavController().navigate(R.id.action_favouritesFragment_to_playerDetailsFragment,bundle)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavouritePlayersBinding = FragmentFavouritePlayersBinding.inflate(inflater,container,false)
}