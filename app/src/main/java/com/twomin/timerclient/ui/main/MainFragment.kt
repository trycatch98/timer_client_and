package com.twomin.timerclient.ui.main

import androidx.fragment.app.viewModels
import com.twomin.timerclient.R
import com.twomin.timerclient.databinding.FragmentMainBinding
import com.twomin.timerclient.ui.base.BaseFragment

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(
    R.layout.fragment_main
) {
    override val viewModel by viewModels<MainViewModel>()
}