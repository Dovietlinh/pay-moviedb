package com.example.movietv.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.movietv.R

class MainActivity : AppCompatActivity() {
    private val TAG = "Home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mFragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
//        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(
            R.id.frame_home,
            FragmentHome(), TAG).commit()
    }
}
