package com.example.movietv.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.movietv.R
import com.google.android.material.tabs.TabLayout

class FragmentHome : Fragment() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tabLayout = view.findViewById(R.id.tabs) as TabLayout
        viewPager = view.findViewById(R.id.viewpager) as ViewPager
        viewPager!!.setAdapter(MyAdapter(fragmentManager))
        tabLayout!!.post(Runnable { tabLayout!!.setupWithViewPager(viewPager) })
        return view
    }

    private inner class MyAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val int_items = 5
        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = FragmentCategory()
                1 -> fragment = FragmentCategory()
                2 -> fragment = FragmentCategory()
                3 -> fragment = FragmentCategory()
                4 -> fragment = FragmentCategory()
                5 -> fragment = FragmentCategory()
            }
            return fragment!!
        }

        override fun getCount(): Int {
            return int_items
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "All"
                1 -> return "Popular"
                2 -> return "Now Playing"
                3 -> return "UpComing"
                4 -> return "Top Rate"
                5 -> return "Genres"
            }
            return null
        }
    }
}