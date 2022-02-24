package com.phillVa.rugbyapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phillVa.rugbyapp.R
import com.phillVa.rugbyapp.ui.home.UsersArticles
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.users_newsfeed.view.*

class Articles : Fragment() {

    lateinit var competitionList: RecyclerView
    private var user = Firebase.auth.currentUser?.uid
    lateinit var db: FirebaseFirestore
    lateinit var connect: FirestoreRecyclerAdapter<UsersArticles, DashboardViewModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        competitionList = view.findViewById(R.id.mainRecycler)
        db = FirebaseFirestore.getInstance()

        var query = db.collection("users/$user/articles")


        val options = FirestoreRecyclerOptions.Builder<UsersArticles>()
            .setQuery(query, UsersArticles::class.java).build()

        connect =
            object : FirestoreRecyclerAdapter<UsersArticles, DashboardViewModel>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): DashboardViewModel {
                    return DashboardViewModel(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.users_newsfeed, parent, false)
                    )
                }

                override fun onBindViewHolder(
                    holder: DashboardViewModel,
                    position: Int,
                    model: UsersArticles
                ) {
                    holder.savedArticles(model)
                }
            }

        val layoutManager = LinearLayoutManager(context)
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
}