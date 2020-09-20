package com.example.mertkesgin.footballapp.ui.matches

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
import com.example.mertkesgin.footballapp.data.entries.MatchDetails
import com.example.mertkesgin.footballapp.data.entries.Matches
import com.example.mertkesgin.footballapp.databinding.FragmentMatchDetailsBinding
import com.example.mertkesgin.footballapp.utils.Constant.getScore
import com.example.mertkesgin.footballapp.utils.ImageHelper
import com.example.mertkesgin.footballapp.utils.ManageProgress.hideProgress
import com.example.mertkesgin.footballapp.utils.ManageProgress.showProgress
import com.example.mertkesgin.footballapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MatchDetailsFragment : Fragment(R.layout.fragment_match_details) {

    val matchViewModel:MatchesViewModel by viewModels()

    private var _binding: FragmentMatchDetailsBinding? = null
    private val binding get() = _binding!!

    val args:MatchDetailsFragmentArgs by navArgs()
    private lateinit var match:Matches
    @Inject
    lateinit var imageHelper: ImageHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d(args.match.eventId)
        match = args.match
        checkIsMatchExistObserver()
        setupMatchDetailsObserver()
        setupBadgeHomeObserver()
        setupBadgeAwayObserver()
        setupSaveMatch()
    }

    private fun setupSaveMatch() {
        binding.imgSaveMatch.setOnClickListener {
            matchViewModel.insertMatch(match)
            Toast.makeText(requireContext(), "Match Saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupMatchDetailsObserver() {
        matchViewModel.getMatchDetails(match.eventId)
        matchViewModel.matchDetails.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        initView(it.eventsDetails[0])
                        matchViewModel.getBadges(it.eventsDetails[0].idHomeTeam!!,it.eventsDetails[0].idAwayTeam!!)
                    }
                }
                is Resource.Error -> {
                    hideProgress(binding.progressBarMatchDetails)
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> { hideProgress(binding.progressBarMatchDetails) }
            }
        })
    }

    private fun setupBadgeHomeObserver() {
        matchViewModel.teamBadgeHome.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let { imageHelper.loadUrl(it.badgeList[0].strTeamBadge!!,binding.imgDetailsHome) }
                    hideProgress(binding.progressBarHomeBadge)
                }
                is Resource.Error -> {
                    hideProgress(binding.progressBarHomeBadge)
                }
                is Resource.Loading -> { showProgress(binding.progressBarHomeBadge) }
            }
        })
    }

    private fun setupBadgeAwayObserver() {
        matchViewModel.teamBadgeAway.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let { imageHelper.loadUrl(it.badgeList[0].strTeamBadge!!,binding.imgDetailsAway) }
                    hideProgress(binding.progressBarAwayBadge)
                }
                is Resource.Error -> {
                    hideProgress(binding.progressBarAwayBadge)
                }
                is Resource.Loading -> { showProgress(binding.progressBarAwayBadge) }
            }
        })
    }

    private fun checkIsMatchExistObserver() {
        matchViewModel.isMatchExist(match.eventId).observe(viewLifecycleOwner,Observer{
            when(it){
                true -> {binding.imgSaveMatch.setImageResource(R.drawable.ic_favourite_fill)}
                else -> {binding.imgSaveMatch.setImageResource(R.drawable.ic_favourite)}
            }
        })
    }

    private fun initView(matchDetails: MatchDetails) {
        binding.tvDetailsLeageName.text = matchDetails.strLeague
        binding.tvMatchResult.text = getScore(matchDetails.intHomeScore,matchDetails.intAwayScore)
        binding.tvDetailsHomeName.text = matchDetails.strHomeTeam
        binding.tvDetailsAwayName.text = matchDetails.strAwayTeam
        binding.tvDetailsDate.text = matchDetails.dateEvent
        binding.tvDetailsTime.text = matchDetails.strTime?.substring(0,5)
        binding.tvHomeGoals.text = matchDetails.strHomeGoalDetails?.replace(";","\n")
        binding.tvAwayGoals.text = matchDetails.strAwayGoalDetails?.replace(";","\n")
        binding.tvHomeShots.text = matchDetails.intHomeShots
        binding.tvAwayShots.text = matchDetails.intAwayShots
        binding.tvHomeFormation.text = matchDetails.strHomeFormation
        binding.tvAwayFormation.text = matchDetails.strAwayFormation
        binding.tvHomeGoalKeeper.text = matchDetails.strHomeLineupGoalkeeper
        binding.tvAwayGoalKeeper.text = matchDetails.strAwayLineupGoalkeeper
        binding.tvHomeDefender.text = matchDetails.strHomeLineupDefense?.replace(";","\n")
        binding.tvAwayDefender.text = matchDetails.strAwayLineupDefense?.replace(";","\n")
        binding.tvHomeMid.text = matchDetails.strHomeLineupMidfield?.replace(";","\n")
        binding.tvAwayMid.text = matchDetails.strAwayLineupMidfield?.replace(";","\n")
        binding.tvHomeForward.text = matchDetails.strHomeLineupForward?.replace(";","\n")
        binding.tvAwayForward.text = matchDetails.strAwayLineupForward?.replace(";","\n")
        binding.imgBackMatchDetails.setOnClickListener { activity?.onBackPressed() }
    }
}