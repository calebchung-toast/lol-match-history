package com.example.lolmatchhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lolmatchhistory.ui.main.MainFragment
import com.example.lolmatchhistory.ui.main.MainViewModel
import com.example.lolmatchhistory.ui.main.MatchHistoryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.summonerName.observe(this, Observer { summonerName ->
            // Perform an action with the latest item data
            System.out.println(summonerName)
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