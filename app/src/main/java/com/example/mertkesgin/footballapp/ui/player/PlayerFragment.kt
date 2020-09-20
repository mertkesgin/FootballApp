package com.example.mertkesgin.footballapp.ui.player

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.PlayerAdapter
import com.example.mertkesgin.footballapp.databinding.FragmentPlayerBinding
import com.example.mertkesgin.footballapp.utils.Constant.SEARCH_PLAYER_TIME_DELAY
import com.example.mertkesgin.footballapp.utils.ManageProgress.hideProgress
import com.example.mertkesgin.footballapp.utils.ManageProgress.showProgress
import com.example.mertkesgin.footballapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : Fragment(R.layout.fragment_player) {

    val playerViewModel: PlayerViewModel by viewModels()

    private var _binding:FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    var job: Job? = null
    @Inject
    lateinit var playerAdapter: PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchPlayer()
        setupPlayerObserver()
    }

    private fun setupPlayerObserver() {
        playerViewModel.player.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let { playerAdapter.differ.submitList(it.players) }
                    hideProgress(binding.progressBarPlayer)
                }
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(requireContext(), "message", Toast.LENGTH_SHORT).show()
                        hideProgress(binding.progressBarPlayer)
                    }
                }
                is Resource.Loading -> { showProgress(binding.progressBarPlayer) }
            }
        })
    }

    private fun setupSearchPlayer() {
        binding.etSearchPlayer.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_PLAYER_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty() && editable.toString().length > 2)
                        playerViewModel.searchPlayer(editable.toString())
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvPlayer.apply {
            adapter = playerAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
        setupRvItemClick()
    }

    private fun setupRvItemClick() {
        playerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("player",it) }
            findNavController().navigate(R.id.action_playerFragment_to_playerDetailsFragment,bundle)
        }
    }
}