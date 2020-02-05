package com.example.movietv.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.movietv.Adapter.ViewPagerAdapter
import com.example.movietv.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    internal lateinit var viewpageradapter: ViewPagerAdapter
    private val TAG = "Home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val mFragmentManager: FragmentManager = supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
////        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.replace(
//            R.id.frame_home,
//            FragmentHome(), TAG
//        ).commit()

        viewpageradapter= ViewPagerAdapter(supportFragmentManager)

        this.viewpagerHome.adapter=viewpageradapter
        this.tabContainer.setupWithViewPager(this.viewpagerHome)

    }

    fun choosePopular(view: View){
        val mFragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(
            R.id.frame_home,
            FragmentCategory(), TAG
        ).commit()
    }
}
