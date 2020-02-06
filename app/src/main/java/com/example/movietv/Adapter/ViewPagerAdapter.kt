package com.example.movietv.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movietv.View.FragmentGenres
import com.example.movietv.View.FragmentHome
import com.example.movietv.View.FragmentSearch

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentHome()
            1 -> fragment = FragmentGenres()
            2 -> fragment = FragmentSearch()
            3 -> fragment = FragmentSearch()
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