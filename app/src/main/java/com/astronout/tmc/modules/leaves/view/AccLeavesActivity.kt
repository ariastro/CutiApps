package com.astronout.tmc.modules.leaves.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAccLeavesBinding
import com.astronout.tmc.modules.leaves.adapter.GetAccLeavesAdapter
import com.astronout.tmc.modules.leaves.view.DetailAccLeavesActivity.Companion.EXTRA_ACC_LEAVE
import com.astronout.tmc.modules.leaves.view.DetailAccLeavesActivity.Companion.REQUEST_DETAIL_ACC_LEAVES
import com.astronout.tmc.modules.leaves.viewmodel.AccLeavesViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.gone
import com.astronout.tmc.utils.showToast
import com.astronout.tmc.utils.visible

class AccLeavesActivity : BaseActivity() {

    private lateinit var binding: ActivityAccLeavesBinding
    private lateinit var viewModel: AccLeavesViewModel

    private lateinit var adapter: GetAccLeavesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_acc_leaves)
        viewModel = ViewModelProvider(this).get(AccLeavesViewModel::class.java)
        binding.accLeaves = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.getAccLeaves()

        viewModel.status.observe(this, Observer {
            if (it == true) {
                binding.noData.visibility = gone()
                binding.swipeRefresh.visibility = visible()
            } else {
                binding.noData.visibility = visible()
                binding.swipeRefresh.visibility = gone()
            }
        })

        viewModel.listLeaves.observe(this, Observer {
            if (it?.isNotEmpty()!!) {
                adapter = GetAccLeavesAdapter(this, GetAccLeavesAdapter.OnClickListener{ getAccLeaves ->
                    val intent = Intent(this, DetailAccLeavesActivity::class.java)
                    intent.putExtra(EXTRA_ACC_LEAVE, getAccLeaves)
                    startActivityForResult(intent, REQUEST_DETAIL_ACC_LEAVES)
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvAccLeave.setHasFixedSize(true)
                binding.rvAccLeave.adapter = adapter
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAccLeaves()
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
        if (requestCode == REQUEST_DETAIL_ACC_LEAVES) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getAccLeaves()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}