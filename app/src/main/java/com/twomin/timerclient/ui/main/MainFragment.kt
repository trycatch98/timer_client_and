package com.twomin.timerclient.ui.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.twomin.timerclient.R
import com.twomin.timerclient.TimeSet
import com.twomin.timerclient.databinding.FragmentMainBinding
import com.twomin.timerclient.ui.common.BaseFragment


class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(
    R.layout.fragment_main
) {
    override val viewModel by viewModels<MainViewModel>()
    private val recentTimeSetAdapter: RecentTimeSetAdapter = RecentTimeSetAdapter()
    private val saveTimeSetAdapter: SaveTimeSetAdapter = SaveTimeSetAdapter()
    private val presetTimeSetAdapter: PresetTimeSetAdapter = PresetTimeSetAdapter()

    override fun initView() {
        super.initView()
        initList(binding.recentList, recentTimeSetAdapter)
        initList(binding.saveList, saveTimeSetAdapter)
        initList(binding.presetList, presetTimeSetAdapter)

        setSwipe(binding.recentList)
        setSwipe(binding.saveList)

        recentTimeSetAdapter.setOnTaskClickListener(object : BaseAdapter.OnTaskClickListener {
            override fun onHideItem(item: TimeSet) {
            }

            override fun onDeleteItem(item: TimeSet) {
                Snackbar.make(binding.root, "삭제 $item", Snackbar.LENGTH_SHORT).show()
                viewModel.deleteTimeSet(item)
            }
        })

        saveTimeSetAdapter.setOnTaskClickListener(object : BaseAdapter.OnTaskClickListener {
            override fun onHideItem(item: TimeSet) {
                Snackbar.make(binding.root, "숨김 $item", Snackbar.LENGTH_SHORT).show()
                viewModel.hideTimeSet(item)
            }

            override fun onDeleteItem(item: TimeSet) {
            }
        })
    }

    override fun initObserve() {
        super.initObserve()
        viewModel.recentTimeSet.observe(viewLifecycleOwner) {
            recentTimeSetAdapter.submitList(it)
        }

        viewModel.saveTimeSet.observe(viewLifecycleOwner) {
            saveTimeSetAdapter.submitList(it)
        }

        viewModel.presetTimeSet.observe(viewLifecycleOwner) {
            presetTimeSetAdapter.submitList(it)
        }
    }

    private fun initList(recyclerView: RecyclerView, listAdapter: BaseAdapter) {
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(TimerDecoration())
            listAdapter.setOnItemClickListener {
                viewModel.clickTimeSet(it)
            }
        }
    }

    private fun setSwipe(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(
            SwipeHelperCallback().apply {
                setClamp(resources.getDimension(R.dimen.swipeClamp))
            }
        )
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}