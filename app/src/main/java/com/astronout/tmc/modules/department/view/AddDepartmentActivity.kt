package com.astronout.tmc.modules.department.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAddDepartmentBinding
import com.astronout.tmc.modules.department.viewmodel.AddDepartmentViewModel
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import com.astronout.tmc.utils.showToast

class AddDepartmentActivity : BaseActivity() {

    private lateinit var binding: ActivityAddDepartmentBinding
    private lateinit var viewModel: AddDepartmentViewModel

    companion object {
        const val REQUEST_ADD_DEPARTMENT = 2113
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_department)
        viewModel = ViewModelProvider(this).get(AddDepartmentViewModel::class.java)
        binding.addDepartment = viewModel

        setSupportActionBar(binding.toolbar)

        setupProgressBar()

        viewModel.status.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(EXTRA_MESSAGE, getString(R.string.add_success))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showToast(getString(R.string.add_failed))
            }
        })

        binding.btnAdd.setOnClickListener {
            when {
                viewModel.departmentName.value!!.isEmpty() -> binding.departmentNameLayout.error =
                    getString(R.string.empty_field)
                viewModel.departmentShortName.value!!.isEmpty() -> binding.departmentShortNameLayout.error =
                    getString(R.string.empty_field)
                viewModel.departmentCode.value!!.isEmpty() -> binding.departmentCodeLayout.error =
                    getString(R.string.empty_field)
                else -> viewModel.addDepartment()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
