package com.astronout.tmc.modules.leaves.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAllLeavesBinding
import com.astronout.tmc.modules.leaves.adapter.GetAllLeavesAdapter
import com.astronout.tmc.modules.leaves.view.DetailAllLeavesActivity.Companion.EXTRA_ALL_LEAVES
import com.astronout.tmc.modules.leaves.view.DetailAllLeavesActivity.Companion.REQUEST_DETAIL_LEAVES
import com.astronout.tmc.modules.leaves.viewmodel.AllLeavesViewModel
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import com.astronout.tmc.utils.showToast

class AllLeavesActivity : BaseActivity() {

    private lateinit var binding: ActivityAllLeavesBinding
    private lateinit var viewModel: AllLeavesViewModel

    private lateinit var adapter: GetAllLeavesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_leaves)
        viewModel = ViewModelProvider(this).get(AllLeavesViewModel::class.java)
        binding.allLeaves = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.getAllLeaves()

        viewModel.listLeaves.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = GetAllLeavesAdapter(this, GetAllLeavesAdapter.OnClickListener{ getAllLeaves ->
                    val intent = Intent(this, DetailAllLeavesActivity::class.java)
                    intent.putExtra(EXTRA_ALL_LEAVES, getAllLeaves)
                    startActivityForResult(intent, REQUEST_DETAIL_LEAVES)
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvAllLeave.setHasFixedSize(true)
                binding.rvAllLeave.adapter = adapter
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAllLeaves()
            binding.swipeRefresh.isRefreshing = false
        }

        setupProgressBar()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupProgressBar() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                progress.show()
            } else {
                progress.dismiss()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_DETAIL_LEAVES) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getAllLeaves()
                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}
