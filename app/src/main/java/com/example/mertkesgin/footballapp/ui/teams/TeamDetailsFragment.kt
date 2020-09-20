package com.example.mertkesgin.footballapp.ui.teams

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
import com.example.mertkesgin.footballapp.data.entries.Team
import com.example.mertkesgin.footballapp.data.entries.TeamDetails
import com.example.mertkesgin.footballapp.databinding.FragmentTeamDetailsBinding
import com.example.mertkesgin.footballapp.utils.ImageHelper
import com.example.mertkesgin.footballapp.utils.ManageProgress.hideProgress
import com.example.mertkesgin.footballapp.utils.ManageProgress.showProgress
import com.example.mertkesgin.footballapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeamDetailsFragment : Fragment(R.layout.fragment_team_details) {

    val teamsViewModel: TeamsViewModel by viewModels()

    private var _binding:FragmentTeamDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: TeamDetailsFragmentArgs by navArgs()
    private lateinit var team:Team

    @Inject
    lateinit var imageHelper: ImageHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        team = args.team
        setupTeamDetailsObserver()
        checkIsTeamExist()
        setupSaveTeam()
    }

    private fun setupSaveTeam() {
        binding.imgSaveTeam.setOnClickListener {
            teamsViewModel.insertTeam(team)
            Toast.makeText(requireContext(), "Team Saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkIsTeamExist() {
        teamsViewModel.isTeamExist(team.teamId).observe(viewLifecycleOwner, Observer {
            when(it){
                true -> {binding.imgSaveTeam.setImageResource(R.drawable.ic_favourite_fill)}
                else -> {binding.imgSaveTeam.setImageResource(R.drawable.ic_favourite)}
            }
        })
    }

    private fun setupTeamDetailsObserver() {
        teamsViewModel.getTeamDetails(team.teamId)
        teamsViewModel.teamDetails.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    response.data?.let {
                        initViews(it.teamDetails[0])
                        hideProgress(binding.progressBarTeamDetails)
                    }
                }
                is Resource.Error ->{
                    response.message?.let {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        hideProgress(binding.progressBarTeamDetails)
                    }
                }
                is Resource.Loading->{ showProgress(binding.progressBarTeamDetails) }
            }
        })
    }

    private fun initViews(teamDetails: TeamDetails) {
        teamDetails.strTeam?.let { binding.tvTeamName.text = it }
        teamDetails.strStadium?.let { binding.tvStadium.text = it }
        teamDetails.intFormedYear?.let { binding.tvTeamFormedYear.text = it }
        teamDetails.strDescriptionEN?.let { binding.tvTeamDesc.text = it }
        teamDetails.strStadiumDescription?.let { binding.tvStadium.text = it }
        teamDetails.strStadium?.let { binding.tvStadiumName.text = it }
        teamDetails.strStadiumThumb?.let { if (it != "") imageHelper.loadUrl(it,binding.imgStadium) }
        teamDetails.strTeamBadge?.let { if (it != "") imageHelper.loadUrl(it,binding.imgTeamLogo) }
        teamDetails.strTeamJersey?.let { if (it != "") imageHelper.loadUrl(it,binding.imgTeamJersey) }
        teamDetails.strTeamFanart1?.let { if (it != "") imageHelper.loadUrl(it,binding.imgFanArt) }
    }
}