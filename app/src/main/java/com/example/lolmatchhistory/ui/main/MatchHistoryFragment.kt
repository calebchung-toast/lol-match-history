package com.example.lolmatchhistory.ui.main

import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.lolmatchhistory.R
import com.example.lolmatchhistory.adapters.MatchAdapter
import com.google.android.material.card.MaterialCardView

class MatchHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = MatchHistoryFragment()
    }

    private lateinit var viewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val view = inflater.inflate(R.layout.match_history, container, false)
        val matches = viewModel.matches.value!!
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = MatchAdapter(requireContext(), matches, parentFragmentManager)

        return view
    }
}