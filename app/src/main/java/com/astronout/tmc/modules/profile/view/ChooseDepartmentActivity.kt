package com.astronout.tmc.modules.profile.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityChooseDepartmentBinding
import com.astronout.tmc.modules.profile.adapter.ChooseDepartmentAdapter
import com.astronout.tmc.modules.profile.viewmodel.ChooseDepartmentViewModel

class ChooseDepartmentActivity : BaseActivity() {

    private lateinit var binding: ActivityChooseDepartmentBinding
    private lateinit var viewModel: ChooseDepartmentViewModel

    private lateinit var adapter: ChooseDepartmentAdapter

    companion object {
        const val REQUEST_DEPARTMENT = 2113
        const val EXTRA_DEPARTMENT = "EXTRA_DEPARTMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_department)
        viewModel = ViewModelProvider(this).get(ChooseDepartmentViewModel::class.java)
        binding.chooseDepartment = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.listDepartment.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = ChooseDepartmentAdapter(ChooseDepartmentAdapter.OnClickListener {getDepartmentModel ->
                    val intent = Intent()
                    intent.putExtra(EXTRA_DEPARTMENT, getDepartmentModel.departmentName)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvDepartment.setHasFixedSize(true)
                binding.rvDepartment.adapter = adapter
            }
        })

        viewModel.getListDepartment()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupSwipeRefressLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListDepartment()
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
