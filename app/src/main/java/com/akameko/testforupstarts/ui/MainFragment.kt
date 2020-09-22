package com.akameko.testforupstarts.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akameko.testforupstarts.MainAdapter
import com.akameko.testforupstarts.R
import com.akameko.testforupstarts.SharedViewModel
import com.akameko.testforupstarts.repository.pojos.Jeans
import com.akameko.testforupstarts.utils.Notificator

class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private lateinit var textViewCounter: TextView

    private var jeansList = arrayListOf<Jeans>()
    private var likedJeansList= arrayListOf<Jeans>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.main_fragment, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initViews(root)
        initFragment()
        return root
    }

    private fun initFragment() {
        //TODO
        sharedViewModel.loadJeans()
        sharedViewModel.jeansList.observe(viewLifecycleOwner, Observer { jeansList: List<Jeans> -> updateFragmentView(jeansList) })
    }

    private fun initViews(rootView: View) {
        // init recyclerView
        recyclerView = rootView.findViewById(R.id.main_recycler_view)
        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            val layoutManager = GridLayoutManager(context, 2)
            this.layoutManager = layoutManager
        }

        // init mainAdapter
        mainAdapter = MainAdapter(jeansList, likedJeansList)
        mainAdapter.apply {
            setOnLikeClickListener { likedJeans: Jeans, _: Int, addToFavourite: Boolean ->
                if (addToFavourite) {
                    sharedViewModel.addToFavourite(likedJeans)
                    Log.d(TAG, "Added to favourite")
                    Notificator.showLikeNotification(rootView, activity)
                    //Log.d("123", jeansDatabase.getJeansDao().getItemById(likedJeans.getId()).toString());
                } else {
                    sharedViewModel.removeFromFavourite(likedJeans)
                    Log.d(TAG, "Removed from favourite")
                }
            }
            setOnItemClickListener { jeansToShow: Jeans, position: Int ->
                sharedViewModel.setDataForDetailFragment(jeansToShow, position)

                //TODO
                (activity as MainActivity?)?.showDetails()
            }

            recyclerView.adapter = this
        }
        textViewCounter = rootView.findViewById(R.id.text_view_main_count)
    }

    private fun updateFragmentView(jeansList: List<Jeans>) {
        this.jeansList.addAll(jeansList)
        this.likedJeansList.addAll(sharedViewModel.getAllFavouritesJeansList())
        mainAdapter.notifyDataSetChanged()

        textViewCounter.text = String.format(getString(R.string.text_counter), jeansList.size)
    }
}