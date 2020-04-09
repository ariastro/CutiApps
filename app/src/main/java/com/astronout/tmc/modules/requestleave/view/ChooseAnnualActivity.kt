package com.astronout.tmc.modules.requestleave.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityChooseAnnualBinding
import com.astronout.tmc.modules.requestleave.adapter.ChooseAnnualAdapter
import com.astronout.tmc.modules.requestleave.viewmodel.ChooseAnnualViewModel
import com.astronout.tmc.utils.showToast

class ChooseAnnualActivity : BaseActivity() {

    private lateinit var binding: ActivityChooseAnnualBinding
    private lateinit var viewModel: ChooseAnnualViewModel

    private lateinit var adapter: ChooseAnnualAdapter

    companion object {
        const val REQUEST_ANNUAL_TYPE = 3123
        const val EXTRA_ANNUAL_TYPE = "EXTRA_ANNUAL_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_annual)
        viewModel = ViewModelProvider(this).get(ChooseAnnualViewModel::class.java)
        binding.chooseAnnual = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.listAnnual.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = ChooseAnnualAdapter(ChooseAnnualAdapter.OnClickListener {getAnnualModel ->
                    val intent = Intent()
                    intent.putExtra(EXTRA_ANNUAL_TYPE, getAnnualModel.leaveType)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvAnnual.setHasFixedSize(true)
                binding.rvAnnual.adapter = adapter
            }
        })

        viewModel.getListAnnual()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupSwipeRefressLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListAnnual()
            binding.swipeRefresh.isRefreshing = false
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
