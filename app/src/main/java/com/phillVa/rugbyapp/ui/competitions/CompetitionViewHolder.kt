package com.phillVa.rugbyapp.ui.competitions

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.phillVa.rugbyapp.R
import com.phillVa.rugbyapp.view.SharedViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.competitions.view.*



class CompetitionViewHolder(val view: View) :
    RecyclerView.ViewHolder(view) {


    fun Competition(competitions: Competitions?) {
        view.competitionName.text = competitions?.name

        val downloadedUrl = competitions?.image

        if (downloadedUrl!!.isEmpty()) {
            // Rimage.setImageResource(R.mipmap.ic_placeholder_icon);
        } else {
            Picasso.get().load(downloadedUrl).fit().into(view.competitionImage)
        }


    }


}