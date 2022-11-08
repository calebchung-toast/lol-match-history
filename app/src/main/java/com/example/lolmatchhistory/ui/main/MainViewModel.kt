package com.example.lolmatchhistory.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Match(
    val matchDate: String = "",
)


class MainViewModel : ViewModel() {

    private val mutableSummonerName = MutableLiveData<String>()
    val summonerName: LiveData<String> get() = mutableSummonerName

    fun searchSummonerName(name: String) {
        mutableSummonerName.value = name
    }
}