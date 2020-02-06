package com.example.movietv.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movietv.View.FragmentHome
import com.example.movietv.View.FragmentCategory
import com.example.movietv.View.FragmentGenres
import com.example.movietv.View.FragmentSearch

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0) {
            fragment = FragmentHome()
        } else if (position == 1) {
            fragment = FragmentSearch()
        } else if (position == 2) {
            fragment = FragmentSearch()
        } else if (position == 3) {
            fragment = FragmentSearch()
        }
        return fragment as Fragment
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "Home"
        } else if (position == 1) {
            title = "Genre"
        } else if (position == 2) {
            title = "Search"
        } else if (position == 3) {
            title = "Favorite"
        }
        return title
    }

}