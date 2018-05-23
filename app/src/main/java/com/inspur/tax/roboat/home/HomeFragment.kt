package com.inspur.tax.roboat.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inspur.tax.roboat.common.obtainViewModel
import com.inspur.tax.roboat.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var dataBinding: FragmentHomeBinding

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentHomeBinding.inflate(inflater, container, false)
                .also { it.viewModel = (activity as HomeActivity).obtainViewModel() }
        return dataBinding.root
    }
}