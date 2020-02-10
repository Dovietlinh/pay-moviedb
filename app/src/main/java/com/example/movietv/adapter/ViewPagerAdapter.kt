package com.example.movietv.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movietv.view.GenresFragment
import com.example.movietv.view.HomeFragment
import com.example.movietv.view.SearchFragment


class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = GenresFragment()
            2 -> fragment = SearchFragment()
            3 -> fragment = SearchFragment()
        }
        return fragment as Fragment
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        when (position) {
            0 -> title = "Home"
            1 -> title = "Genre"
            2 -> title = "Search"
            3 -> title = "Favorite"
        }
        return title
    }

}