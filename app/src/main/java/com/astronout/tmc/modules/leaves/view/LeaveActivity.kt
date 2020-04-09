package com.astronout.tmc.modules.leaves.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityLeaveBinding
import com.astronout.tmc.modules.leaves.adapter.GetLeaveByIdAdapter
import com.astronout.tmc.modules.leaves.model.GetLeaveByIdModel
import com.astronout.tmc.modules.leaves.viewmodel.LeaveViewModel
import com.astronout.tmc.utils.gone
import com.astronout.tmc.utils.showToast
import com.astronout.tmc.utils.visible
import kotlinx.android.synthetic.main.activity_leave.*

class LeaveActivity : BaseActivity() {

    private lateinit var viewModel: LeaveViewModel
    private lateinit var binding: ActivityLeaveBinding
    private lateinit var adapter: GetLeaveByIdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_leave)
        viewModel = ViewModelProvider(this).get(LeaveViewModel::class.java)
        binding.leave = viewModel

        setupToolbar()

        viewModel.getListLeaveById()

        viewModel.status.observe(this, Observer {
            if (it == true) {
                binding.noRiwayat.visibility = gone()
                binding.swipeRefresh.visibility = visible()
            } else {
                binding.noRiwayat.visibility = visible()
                binding.swipeRefresh.visibility = gone()
            }
        })

        viewModel.listLeaveById.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = GetLeaveByIdAdapter(this, GetLeaveByIdAdapter.OnClickListener{getLeaveByIdModel ->
                    val intent = Intent(this, DetailLeaveActivity::class.java)
                    intent.putExtra(DetailLeaveActivity.EXTRA_LEAVE, getLeaveByIdModel)
                    startActivity(intent)
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvLeave.setHasFixedSize(true)
                binding.rvLeave.adapter = adapter
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListLeaveById()
            binding.swipeRefresh.isRefreshing = false
        }

        setupProgressBar()

    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.toolbar.findViewById(R.id.base_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
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

}
