package com.plvaloyi.rugbyapp.ui.upcoming

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class matchesAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return fixture()
            }
            1 -> {
                return results()
            }
            else -> {
                return fixture()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "Fixture"
            }
            1 -> {
                return "Results"
            }
        }
        return super.getPageTitle(position)
    }

}