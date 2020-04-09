package com.astronout.tmc.modules.department.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityDepartmentBinding
import com.astronout.tmc.modules.department.adapter.DepartmentAdapter
import com.astronout.tmc.modules.department.view.AddDepartmentActivity.Companion.REQUEST_ADD_DEPARTMENT
import com.astronout.tmc.modules.department.view.UpdateDepartmentActivity.Companion.REQUEST_UPDATE_DEPARTMENT
import com.astronout.tmc.modules.department.viewmodel.DepartmentViewModel
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import com.astronout.tmc.utils.showToast

class DepartmentActivity : BaseActivity() {

    private lateinit var binding: ActivityDepartmentBinding
    private lateinit var viewModel: DepartmentViewModel

    private lateinit var adapter: DepartmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_department)
        viewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        binding.listDepartment = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.listDepartment.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = DepartmentAdapter(DepartmentAdapter.OnClickListener {getDepartmentModel ->
                    val sheet = SheetDepartmentFragment()
                    sheet.setDepartment(getDepartmentModel)
                    sheet.show(supportFragmentManager, "SheetDepartmentFragment")
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvDepartment.setHasFixedSize(true)
                binding.rvDepartment.adapter = adapter
            }
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                viewModel.getListDepartment()
                showToast(getString(R.string.delete_success))
            } else {
                showToast(getString(R.string.delete_failed))
            }
        })

        viewModel.getListDepartment()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun removeDepartment(departmentId: String) {
        viewModel.setDepartmentId(departmentId)
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.delete_alert))
        alert.setPositiveButton(android.R.string.yes) { _, _ ->
            viewModel.deleteDepartment()
        }
        alert.setNegativeButton(android.R.string.no) { dialog, _ ->
            dialog.cancel()
        }
        alert.show()
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
        viewModel.isLoadingDelete.observe(this, Observer {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add){
            startActivityForResult(Intent(this, AddDepartmentActivity::class.java), REQUEST_ADD_DEPARTMENT)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_DEPARTMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListDepartment()
                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
                }
            }
        } else if (requestCode == REQUEST_UPDATE_DEPARTMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListDepartment()
                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}