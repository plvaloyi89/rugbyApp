package com.plvaloyi.rugbyapp.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class DashboardMyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return Articles()
            }
            1 -> {
                return Upcoming()
            }
            2 -> {
                return results()
            }
            else -> {
                return Articles()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "Articles"
            }
            1 -> {
                return "Upcoming"
            }
            2 -> {
                return "Results"
            }
        }
        return super.getPageTitle(position)
    }

}