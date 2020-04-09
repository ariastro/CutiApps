package com.astronout.tmc.modules.employees.view

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
import com.astronout.tmc.databinding.ActivityEmployeesBinding
import com.astronout.tmc.modules.employees.adapter.EmployeesAdapter
import com.astronout.tmc.modules.employees.view.AddEmployeeActivity.Companion.REQUEST_ADD_EMPLOYEE
import com.astronout.tmc.modules.employees.view.UpdateEmployeeActivity.Companion.REQUEST_UPDATE_EMPLOYEE
import com.astronout.tmc.modules.employees.viewmodel.EmployeesViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class EmployeesActivity : BaseActivity() {

    private lateinit var binding: ActivityEmployeesBinding
    private lateinit var viewModel: EmployeesViewModel

    private lateinit var adapter: EmployeesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_employees)
        viewModel = ViewModelProvider(this).get(EmployeesViewModel::class.java)
        binding.listEmployees = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.listEmployees.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter = EmployeesAdapter(EmployeesAdapter.OnClickListener {getAllEmployees ->
                    val sheet = SheetEmployeesFragment()
                    sheet.setEmployees(getAllEmployees)
                    sheet.show(supportFragmentManager, "SheetEmployeesFragment")
                })
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rvAllEmployees.setHasFixedSize(true)
                binding.rvAllEmployees.adapter = adapter
            }
        })

        viewModel.status.observe(this, Observer {
            if (it) {
                viewModel.getListEmployees()
                showToast(getString(R.string.delete_success))
            } else {
                showToast(getString(R.string.delete_failed))
            }
        })

        viewModel.getListEmployees()

        setupProgressBar()
        setupSwipeRefressLayout()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun removeEmployee(id: String) {
        viewModel.setId(id)
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.delete_alert))
        alert.setPositiveButton(android.R.string.yes) { _, _ ->
            viewModel.deleteEmployee()
        }
        alert.setNegativeButton(android.R.string.no) { dialog, _ ->
            dialog.cancel()
        }
        alert.show()
    }

    private fun setupSwipeRefressLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListEmployees()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add){
            startActivityForResult(Intent(this, AddEmployeeActivity::class.java), REQUEST_ADD_EMPLOYEE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_EMPLOYEE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListEmployees()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        } else if (requestCode == REQUEST_UPDATE_EMPLOYEE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getListEmployees()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}
