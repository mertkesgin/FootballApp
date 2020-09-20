package com.example.mertkesgin.footballapp.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.data.entries.Player
import com.example.mertkesgin.footballapp.data.entries.PlayerDetails
import com.example.mertkesgin.footballapp.databinding.FragmentPlayerDetailsBinding
import com.example.mertkesgin.footballapp.utils.ImageHelper
import com.example.mertkesgin.footballapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayerDetailsFragment : Fragment(R.layout.fragment_player_details) {

    val playerViewModel:PlayerViewModel by viewModels()

    private var _binding: FragmentPlayerDetailsBinding? = null
    private val binding get() = _binding!!

    private val args:PlayerDetailsFragmentArgs by navArgs()
    private lateinit var player:Player
    @Inject
    lateinit var imageHelper: ImageHelper

    private var isExist = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player = args.player
        setupPlayerDetailsObserver()
        checkIsTeamExistObserber()
        setupSavePlayer()
    }

    private fun setupSavePlayer() {
        binding.imgSavePlayer.setOnClickListener {
            if (!isExist){
                playerViewModel.insertPlayer(player)
                Toast.makeText(requireContext(), "Player Saved", Toast.LENGTH_SHORT).show()
            }else playerViewModel.deletePlayer(player)
        }
    }

    private fun checkIsTeamExistObserber() {
        playerViewModel.isPlayerExist(player.playerId).observe(viewLifecycleOwner, Observer {
            isExist = it
            when(it){
                true -> {binding.imgSavePlayer.setImageResource(R.drawable.ic_favourite_fill)}
                else -> {binding.imgSavePlayer.setImageResource(R.drawable.ic_favourite)}
            }
        })
    }

    private fun setupPlayerDetailsObserver() {
        playerViewModel.getPlayerDetails(player.playerId)
        playerViewModel.playerDetails.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        bindView(it.playerDetails[0])
                    }
                }
            }
        })
    }

    private fun bindView(playerDetails: PlayerDetails) {
        playerDetails.strCutout?.let { if (it.isNotEmpty()) imageHelper.loadUrl(playerDetails.strCutout,binding.imgPlayer) }
        playerDetails.strPlayer?.let { binding.tvPlayerName.text = it }
        playerDetails.strTeam?.let { binding.tvPlayerTeam.text = it }
        playerDetails.dateBorn?.let { binding.tvPlayerBorn.text = it }
        playerDetails.strHeight?.let { binding.tvPlayerHeight.text = it }
        playerDetails.strNationality?.let { binding.tvPlayerNationality.text = it }
        playerDetails.strPosition?.let { binding.tvPlayerPosition.text = it }
        playerDetails.strNumber?.let { binding.tvPlayerNumber.text = it }
        playerDetails.strDescriptionEN?.let { binding.tvPlayerDescriptipn.text = it }
        binding.imgPlayerDetailsBack.setOnClickListener { activity?.onBackPressed() }
    }
}