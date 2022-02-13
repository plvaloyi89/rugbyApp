package com.phillVa.rugbyapp.ui.competition

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.phillVa.rugbyapp.ui.competition.competitionLayouts.fixtures
import com.phillVa.rugbyapp.ui.competition.competitionLayouts.news
import com.phillVa.rugbyapp.ui.competition.competitionLayouts.results
import com.phillVa.rugbyapp.ui.competition.competitionLayouts.standings

class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return news()
            }
            1 -> {
                return standings()
            }
            2 -> {
                return fixtures()
            }
            3 -> {
                return results()
            }
            else -> {
                return news()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "News"
            }
            1 -> {
                return "Standing"
            }
            2 -> {
                return "Fixture"
            }
            3 -> {
                return "Results"
            }
        }
        return super.getPageTitle(position)
    }

}