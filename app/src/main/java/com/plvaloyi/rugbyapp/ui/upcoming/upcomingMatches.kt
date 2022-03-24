package com.plvaloyi.rugbyapp.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.plvaloyi.rugbyapp.R
import com.google.android.material.tabs.TabLayout


class upcomingMatches : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.upcoming_matches_results_fragment, container, false)

        val viewPager = root.findViewById<ViewPager>(R.id.matchesViewPager)
        viewPager.adapter = matchesAdapter(childFragmentManager)

        val tabLayout = root.findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)



        return root
    }


    companion object {
        fun newInstance() = upcomingMatches()
    }

}