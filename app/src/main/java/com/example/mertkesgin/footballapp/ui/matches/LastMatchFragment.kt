package com.example.mertkesgin.footballapp.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.MatchesAdapter
import com.example.mertkesgin.footballapp.databinding.FragmentLastMatchBinding
import com.example.mertkesgin.footballapp.utils.LeagueIdProvider
import com.example.mertkesgin.footballapp.utils.ManageProgress.hideProgress
import com.example.mertkesgin.footballapp.utils.ManageProgress.showProgress
import com.example.mertkesgin.footballapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LastMatchFragment : Fragment(R.layout.fragment_last_match) {

    private val matchesViewModel: MatchesViewModel by viewModels()

    private var _binding:FragmentLastMatchBinding?=null
    private val binding get() = _binding!!
    @Inject
    lateinit var matchesAdapter:MatchesAdapter
    @Inject
    lateinit var spinnerAdapter: ArrayAdapter<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLastMatchBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        sendResponseWithSpinner()
        setupLastMatchesObserver()
    }

    private fun setupLastMatchesObserver() {
        matchesViewModel.lastMatches.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    response.data?.let {
                        matchesAdapter.differ.submitList(it.results)
                        hideProgress(binding.progressBarLastMatches)
                    }
                }
                is Resource.Error ->{
                    response.message?.let {
                        hideProgress(binding.progressBarLastMatches)
                    }
                }
                is Resource.Loading ->{ showProgress(binding.progressBarLastMatches) }
            }
        })
    }

    private fun sendResponseWithSpinner() {
        binding.spinnerLast.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val league = parent?.getItemAtPosition(position).toString()
                val leagueId = LeagueIdProvider.getLeagueId(league)
                matchesViewModel.getLastMatches(leagueId)
            }
        }
    }

    private fun setupRecyclerview() {
        binding.rvLastMatches.apply {
            adapter = matchesAdapter
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        }
        setupRvClick()
    }

    private fun setupRvClick() {
        matchesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("match",it) }
            findNavController().navigate(R.id.action_matchFragment_to_matchDetailsFragment,bundle)
        }
    }
}