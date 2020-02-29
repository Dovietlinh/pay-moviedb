package com.example.movietv.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movietv.R
import com.example.movietv.view.FavoriteFragment
import com.example.movietv.view.HomeFragment
import com.example.movietv.view.SearchFragment


class ViewPagerAdapter(fm: FragmentManager,vContext: Context) : FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val context:Context=vContext
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()
//            1 -> fragment = GenresFragment()
            1 -> fragment = SearchFragment()
            2 -> fragment = FavoriteFragment()
        }
        return fragment as Fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        when (position) {
            0 -> title = context.getString(R.string.home)
//            1 -> title = context.getString(R.string.genre_title)
            1 -> title = context.getString(R.string.search)
            2 -> title = context.getString(R.string.favorite)
        }
        return title
    }

}