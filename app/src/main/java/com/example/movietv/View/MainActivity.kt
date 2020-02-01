package com.example.movietv.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.movietv.R
import com.example.themoviedb.Api.ApiService
import com.example.themoviedb.Api.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_all.*

class MainActivity : AppCompatActivity(){

    private val TAG = "Home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mFragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(
            R.id.frame_home,
            FragmentHome(), TAG
        ).commit()
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
