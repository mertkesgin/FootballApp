package com.example.mertkesgin.footballapp.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourites.view.*

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites,container,false)
        setupViewPagerWithTabLayout(view)
        return view
    }

    private fun setupViewPagerWithTabLayout(view: View) {
        val fragmentList = arrayListOf<Fragment>(
            FavouriteMatchesFragment(),
            FavouriteTeamsFragment(),
            FavouritePlayersFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        view.view_pager_favourites.adapter = adapter

        TabLayoutMediator(view.tab_layout_favourites,view.view_pager_favourites){ tab, position ->
            when(position){
                0 -> {tab.text = "Matches"}
                1 -> {tab.text = "Teams"}
                2 -> {tab.text = "Players"}
                else -> {/*DO NOTHING*/}
            }
        }.attach()
    }
}