package com.plvaloyi.rugbyapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.plvaloyi.rugbyapp.R
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.plvaloyi.rugbyapp.view.SharedViewModel

class DashboardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val viewPager = root.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = DashboardMyAdapter(childFragmentManager)

        val tabLayout = root.findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        return root
    }

}