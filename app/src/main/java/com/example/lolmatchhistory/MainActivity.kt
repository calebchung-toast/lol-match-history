package com.example.lolmatchhistory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lolmatchhistory.ui.main.MainFragment
import com.example.lolmatchhistory.ui.main.MainViewModel
import com.example.lolmatchhistory.ui.main.MatchHistoryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel


    init {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.matches.observe(this, Observer {
            // When the have been changed, change to match history fragment
            supportFragmentManager.beginTransaction().replace(R.id.container, MatchHistoryFragment.newInstance()).commitNow()
        })
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .setReorderingAllowed(true)
                .commitNow()
        }
    }


}