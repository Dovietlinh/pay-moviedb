package com.example.movietv.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.movietv.R
import com.example.movietv.adapter.ViewPagerAdapter
import com.example.movietv.common.Constants.Companion.API_NOW_PLAYING
import com.example.movietv.common.Constants.Companion.API_POPULAR
import com.example.movietv.common.Constants.Companion.API_TOPRATE
import com.example.movietv.common.Constants.Companion.API_UPCOMING
import com.example.movietv.common.Constants.Companion.TITLE_CATEGORY
import com.example.movietv.common.Constants.Companion.TYPE_MOVIE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewpageradapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewpageradapter = ViewPagerAdapter(supportFragmentManager)

        viewpagerHome.adapter = viewpageradapter
        tabContainer.setupWithViewPager(viewpagerHome)
    }

    fun choosePopular(view: View) {
        chooseCategory(API_POPULAR, getString(R.string.popular))
    }

    fun chooseNowPlaying(view: View) {
        chooseCategory(API_NOW_PLAYING, getString(R.string.now_playing))
    }

    fun chooseUpcoming(view: View) {
        chooseCategory(API_UPCOMING, getString(R.string.upcoming))
    }

    fun chooseTopRate(view: View) {
        chooseCategory(API_TOPRATE, getString(R.string.top_rate))
    }

    private fun chooseCategory(type: String, title: String) {
        val b = Bundle()
        b.putString(TYPE_MOVIE, type)
        b.putString(TITLE_CATEGORY, title)
        val mFragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
        val fragmentCategory = CategoryFragment()
        fragmentCategory.arguments = b
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(
            R.id.frame_home,
            fragmentCategory
        ).commit()
    }

    fun backFragmentHome(view: View) {
        onBackPressed()
    }
}
