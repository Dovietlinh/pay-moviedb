@file:Suppress("SpellCheckingInspection")

package com.example.movietv.View

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.movietv.Adapter.ViewPagerAdapter
import com.example.movietv.Common.Constants.Companion.API_NOW_PLAYING
import com.example.movietv.Common.Constants.Companion.API_POPULAR
import com.example.movietv.Common.Constants.Companion.API_TOPRATE
import com.example.movietv.Common.Constants.Companion.API_UPCOMING
import com.example.movietv.Common.Constants.Companion.STRING_TITLE_NOW_PLAYING
import com.example.movietv.Common.Constants.Companion.STRING_TITLE_POPULAR
import com.example.movietv.Common.Constants.Companion.STRING_TITLE_TOPRATE
import com.example.movietv.Common.Constants.Companion.STRING_TITLE_UPCOMING
import com.example.movietv.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewpageradapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewpageradapter = ViewPagerAdapter(supportFragmentManager)

        this.viewpagerHome.adapter = viewpageradapter
        this.tabContainer.setupWithViewPager(this.viewpagerHome)
    }

    fun choosePopular(view: View) {
        chooseCategory(API_POPULAR, STRING_TITLE_POPULAR)
    }

    fun chooseNowPlaying(view: View) {
        chooseCategory(API_NOW_PLAYING, STRING_TITLE_NOW_PLAYING)
    }

    fun chooseUpcoming(view: View) {
        chooseCategory(API_UPCOMING, STRING_TITLE_UPCOMING)
    }

    fun chooseTopRate(view: View) {
        chooseCategory(API_TOPRATE, STRING_TITLE_TOPRATE)
    }

    private fun chooseCategory(type: String, title: String) {
        val b = Bundle()
        b.putString("type", type)
        b.putString("title", title)
        val mFragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
        val fragmentCategory = FragmentCategory()
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
