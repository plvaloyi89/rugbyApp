package com.phillVa.rugbyapp.ui.competition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.viewpager.widget.ViewPager
import com.phillVa.rugbyapp.R
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso


class CompetitionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_competition, container, false)


        val viewPager = root.findViewById<ViewPager>(R.id.viewPager)
        var compName = root.findViewById<TextView>(R.id.compName)
        var compImage = root.findViewById<ImageView>(R.id.competitionimageView)
        viewPager.adapter = MyAdapter(childFragmentManager)

        val tabLayout = root.findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        setFragmentResultListener("competitionName") { requestKey, bundle ->

            compName.text = bundle.getString("competition_name")
            (activity as AppCompatActivity).supportActionBar?.title =
                bundle.getString("competition_name")

        }

        setFragmentResultListener("competitionImage") { requestKey, bundle ->

            var image_url = bundle.getString("competition_image")

            if (image_url!!.isEmpty()) {
                // Rimage.setImageResource(R.mipmap.ic_placeholder_icon);
            } else {
                Picasso.get().load(image_url)
                    .fit().into(compImage)
            }

        }

        return root
    }


}