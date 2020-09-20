package com.example.mertkesgin.footballapp.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.TeamsAdapter
import com.example.mertkesgin.footballapp.databinding.FragmentTeamsBinding
import com.example.mertkesgin.footballapp.utils.Constant.GRID_LAYOUT_COLUMN_COUNT
import com.example.mertkesgin.footballapp.utils.ManageProgress.hideProgress
import com.example.mertkesgin.footballapp.utils.ManageProgress.showProgress
import com.example.mertkesgin.footballapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeamsFragment : Fragment(R.layout.fragment_teams) {

    val teamsViewModel: TeamsViewModel by viewModels()

    private var _binding: FragmentTeamsBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var teamsAdapter:TeamsAdapter
    @Inject
    lateinit var spinnerAdapter:ArrayAdapter<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        sendResponseWithSpinner()
        setupTeamsObserver()
    }

    private fun setupTeamsObserver() {
        teamsViewModel.teams.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    response.data?.let {
                        teamsAdapter.differ.submitList(it.teams)
                        hideProgress(binding.progressBarTeams)
                    }
                }
                is Resource.Error ->{
                    response.message?.let {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        hideProgress(binding.progressBarTeams)
                    }
                }
                is Resource.Loading->{ showProgress(binding.progressBarTeams) }
            }
        })
    }

    private fun sendResponseWithSpinner() {
        binding.spinnerTeam.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {/*DO-NOTHING*/}
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val league = parent?.getItemAtPosition(position).toString()
                teamsViewModel.getTeams(league)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTeams.apply {
            adapter = teamsAdapter
            layoutManager = GridLayoutManager(activity,GRID_LAYOUT_COLUMN_COUNT,GridLayoutManager.VERTICAL,false)
        }
        setupRvItemClick()
    }

    private fun setupRvItemClick() {
        teamsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("team",it) }
            findNavController().navigate(R.id.action_teamsFragment_to_teamDetailsFragment,bundle)
        }
    }
}