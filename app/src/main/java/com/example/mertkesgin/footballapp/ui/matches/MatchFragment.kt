package com.example.mertkesgin.footballapp.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_match.view.*

@AndroidEntryPoint
class MatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_match,container,false)
        setupViewPagerWithTabLayout(view)
        return view
    }

    private fun setupViewPagerWithTabLayout(view: View) {
        val fragmentList = arrayListOf<Fragment>(
            LastMatchFragment(),
            NextMatchFragment()
        )
        val adapter =
            ViewPagerAdapter(
                fragmentList,
                requireActivity().supportFragmentManager,
                lifecycle
            )
        view.view_pager_match.adapter = adapter

        TabLayoutMediator(view.tab_layout_match,view.view_pager_match){ tab,position ->
            when(position){
                0 -> {tab.text = "Results"}
                1 -> {tab.text = "Next Matches"}
                else -> {/*DO NOTHING*/}
            }
        }.attach()
    }
}