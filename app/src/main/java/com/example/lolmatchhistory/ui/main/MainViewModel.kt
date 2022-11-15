package com.example.lolmatchhistory.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lolmatchhistory.models.Match
import com.example.lolmatchhistory.models.MatchHistoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow




class MainViewModel : ViewModel() {

    private val model = MatchHistoryModel()
    private val mutableSummonerName = MutableLiveData<String>()
    private val mutableMatchList = MutableLiveData<List<Match>>()
    val summonerName: LiveData<String> get() = mutableSummonerName
    val matches: LiveData<List<Match>> get() = mutableMatchList

    fun searchSummonerName(name: String) {
        mutableSummonerName.postValue(name)
    }

    private fun setMatchList(matches: List<Match>) {
        mutableMatchList.postValue(matches)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMatches() {
        setMatchList(model.getMatches(summonerName.value!!))
    }
}