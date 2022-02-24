package com.phillVa.rugbyapp.ui.competitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phillVa.rugbyapp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.phillVa.rugbyapp.view.SharedViewModel
import kotlinx.android.synthetic.main.competitions.view.*


class CompetitionsFragment : Fragment() {

    lateinit var db: FirebaseFirestore
    lateinit var competitionList: RecyclerView
    lateinit var connect: FirestoreRecyclerAdapter<Competitions, CompetitionViewHolder>
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        competitionList = view.findViewById(R.id.mainRecycler)
        db = FirebaseFirestore.getInstance()
        var query = db.collection("competitions")

        val options = FirestoreRecyclerOptions.Builder<Competitions>()
            .setQuery(query, Competitions::class.java)
            .build()

        connect = object : FirestoreRecyclerAdapter<Competitions, CompetitionViewHolder>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): CompetitionViewHolder {
                return CompetitionViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.competitions, parent, false)
                )
            }

            override fun onBindViewHolder(
                holder: CompetitionViewHolder,
                position: Int,
                model: Competitions
            ) {
                holder.Competition(model)

                holder.view.setOnClickListener {
                    val action = R.id.action_navigation_notifications_to_compfragment
                    view.findNavController().navigate(action)

                    setFragmentResult("competitionName", bundleOf("competition_name" to model.name))
                    setFragmentResult(
                        "competitionImage",
                        bundleOf("competition_image" to model.image)
                    )
                    viewModel.CompName(model.name)

                }

            }


        }

        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.reverseLayout = false
        competitionList.setHasFixedSize(true)
        competitionList.layoutManager = layoutManager
        competitionList.itemAnimator = null
        competitionList.adapter = connect


    }

    override fun onStart() {
        super.onStart()
        connect.startListening()
    }

    override fun onStop() {
        super.onStop()
        connect.stopListening()
    }


    companion object {
        fun newInstance() = CompetitionsFragment()
    }


}