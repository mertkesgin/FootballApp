package com.example.mertkesgin.footballapp.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.MatchesAdapter
import com.example.mertkesgin.footballapp.base.BaseFragment
import com.example.mertkesgin.footballapp.databinding.FragmentNextMatchBinding
import com.example.mertkesgin.footballapp.utils.LeagueIdProvider
import com.example.mertkesgin.footballapp.utils.ManageProgress.hideProgress
import com.example.mertkesgin.footballapp.utils.ManageProgress.showProgress
import com.example.mertkesgin.footballapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NextMatchFragment : BaseFragment<FragmentNextMatchBinding>() {

    private val matchesViewModel: MatchesViewModel by viewModels()

    @Inject
    lateinit var matchesAdapter:MatchesAdapter
    @Inject
    lateinit var spinnerAdapter:ArrayAdapter<CharSequence>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        sendResponseWithSpinner()
        setupNextMatchesObserver()
    }

    private fun setupNextMatchesObserver() {
        matchesViewModel.nextMatches.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                   response.data?.let { matchesAdapter.differ.submitList(it.results) }
                    hideProgress(binding.progressBarNextMatches)
                }
                is Resource.Error ->{
                    response.message?.let {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        hideProgress(binding.progressBarNextMatches)
                    }
                }
                is Resource.Loading ->{ showProgress(binding.progressBarNextMatches) }
            }
        })
    }

    private fun sendResponseWithSpinner() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val league = parent?.getItemAtPosition(position).toString()
                val leagueId = LeagueIdProvider.getLeagueId(league)
                matchesViewModel.getNextMatches(leagueId)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvNextMatches.apply {
            adapter = matchesAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        }
        setupRvClick()
    }

    private fun setupRvClick() {
        matchesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("match",it) }
            findNavController().navigate(R.id.action_matchFragment_to_matchDetailsFragment,bundle)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNextMatchBinding = FragmentNextMatchBinding.inflate(inflater,container,false)
}