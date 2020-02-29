package com.example.movietv.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietv.R
import com.example.movietv.adapter.MoviePagedListAdapter
import com.example.movietv.data.remote.entity.Movie
import com.example.movietv.viewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_home.rcv_nowPlaying
import kotlinx.android.synthetic.main.fragment_home.rcv_popular
import kotlinx.android.synthetic.main.fragment_home.rcv_topRate
import kotlinx.android.synthetic.main.fragment_home.rcv_upcoming

class HomeFragment : Fragment() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieListAdapter: MoviePagedListAdapter
    private lateinit var viewFlipper: ViewFlipper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initView(view: View) {
        viewFlipper = view.findViewById(R.id.viewFlipper)
        val imgBackground = listOf(R.drawable.img1, R.drawable.img2, R.drawable.img3)
        for (img in imgBackground) {
            val imageView = ImageView(context)
            imageView.setImageResource(R.drawable.background_image_banner)
            imageView.setBackgroundResource(img)
            viewFlipper?.addView(imageView)
            viewFlipper?.flipInterval = 4000
            viewFlipper?.isAutoStart = true
            viewFlipper?.setInAnimation(context, android.R.anim.slide_in_left)
            viewFlipper?.setOutAnimation(context, android.R.anim.slide_out_right)
        }
    }

    private fun initAdapter() {
        viewModel = getViewModel()
        //set Adapter movieList popular
        movieListAdapter = MoviePagedListAdapter(context!!)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcv_popular?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = linearLayoutManager
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapter
        }
        viewModel.moviePagedList.observe(viewLifecycleOwner, Observer<PagedList<Movie>> {
            movieListAdapter.submitList(it)
        })

//        val remoteConfig = FirebaseRemoteConfig.getInstance()
//        remoteConfig.setConfigSettings(
//            FirebaseRemoteConfigSettings.Builder()
//                .setDeveloperModeEnabled(true)
//                .build()
//        )
//        remoteConfig.setDefaults(R.xml.default_map)
//        remoteConfig.fetch(0).addOnCompleteListener { task ->
//            remoteConfig.activateFetched()
//            if (task.isSuccessful) {
//                val isNowPlayingTabDisplay =
//                    remoteConfig.getBoolean(Constants.IS_UPCOMING_DISPLAYED)
//                if (!isNowPlayingTabDisplay) {
//                    rcv_upcoming.visibility = View.GONE
//                    txtUpcoming.visibility = View.GONE
//                } else {
//                    //set Adapter movieList upcoming
//                    val movieListAdapterUpcoming = MoviePagedListAdapter(context!!)
//                    rcv_upcoming?.apply {
//                        this.setHasFixedSize(true)
//                        this.layoutManager =
//                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                        this.itemAnimator = DefaultItemAnimator()
//                        this.adapter = movieListAdapterUpcoming
//                    }
//                    viewModel.moviePagedListUpcoming.observe(
//                        viewLifecycleOwner,
//                        Observer<PagedList<Movie>> {
//                            movieListAdapterUpcoming.submitList(it)
//                        })
//                }
//            }
//        }
        //set Adapter movieList upcoming
        val movieListAdapterUpcoming = MoviePagedListAdapter(context!!)
        rcv_upcoming?.apply {
            this.setHasFixedSize(true)
            this.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapterUpcoming
        }

        viewModel.moviePagedListUpcoming.observe(viewLifecycleOwner,
            Observer<PagedList<Movie>> {
                movieListAdapterUpcoming.submitList(it)
            })
        //set Adapter movieList now playing
        val movieListAdapterNowPlaying = MoviePagedListAdapter(context!!)
        rcv_nowPlaying?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapterNowPlaying
        }
        viewModel.moviePagedListNowPlaying.observe(viewLifecycleOwner, Observer<PagedList<Movie>> {
            movieListAdapterNowPlaying.submitList(it)
        })

        //set Adapter movieList topRate
        val movieListAdapterTopRate = MoviePagedListAdapter(context!!)
        rcv_topRate?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapterTopRate
        }
        viewModel.moviePagedListTopRate.observe(viewLifecycleOwner, Observer<PagedList<Movie>> {
            movieListAdapterTopRate.submitList(it)
        })
    }

    private fun getViewModel(): MovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(activity!!.application) as T
            }
        })[MovieViewModel::class.java]
    }

}
