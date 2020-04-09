package com.astronout.tmc.modules.requestleave.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityChooseNonAnnualBinding
import com.astronout.tmc.modules.requestleave.adapter.ChooseNonAnnualAdapter
import com.astronout.tmc.modules.requestleave.viewmodel.ChooseNonAnnualViewModel

class ChooseNonAnnualActivity : BaseActivity() {

    private lateinit var binding: ActivityChooseNonAnnualBinding
    private lateinit var viewModel: ChooseNonAnnualViewModel

    private lateinit var adapter: ChooseNonAnnualAdapter

    companion object {
        const val REQUEST_NON_ANNUAL_TYPE = 3123
        const val EXTRA_NON_ANNUAL_TYPE = "EXTRA_NON_ANNUAL_TYPE"
        const val EXTRA_RIGHTS_GRANTED = "EXTRA_RIGHTS_GRANTED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_non_annual)
        viewModel = ViewModelProvider(this).get(ChooseNonAnnualViewModel::class.java)
        binding.chooseNonAnnual = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.listNonAnnual.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = ChooseNonAnnualAdapter(ChooseNonAnnualAdapter.OnClickListener {getNonAnnualModel ->
                    val intent = Intent()
                    intent.putExtra(EXTRA_NON_ANNUAL_TYPE, getNonAnnualModel.leaveType)
                    intent.putExtra(EXTRA_RIGHTS_GRANTED, getNonAnnualModel.rightsGranted)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvNonAnnual.setHasFixedSize(true)
                binding.rvNonAnnual.adapter = adapter
            }
        })

        viewModel.getListNonAnnual()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupSwipeRefressLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListNonAnnual()
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