package com.astronout.tmc.modules.department.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityUpdateDepartmentBinding
import com.astronout.tmc.modules.department.model.GetDepartmentModel
import com.astronout.tmc.modules.department.viewmodel.UpdateDepartmentViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class UpdateDepartmentActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateDepartmentBinding
    private lateinit var viewModel: UpdateDepartmentViewModel

    companion object {
        const val REQUEST_UPDATE_DEPARTMENT = 3124
        const val EXTRA_DEPARTMENT = "EXTRA_DEPARTMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_department)
        viewModel = ViewModelProvider(this).get(UpdateDepartmentViewModel::class.java)
        binding.updateDepartment = viewModel

        setSupportActionBar(binding.toolbar)

        if (intent.hasExtra(EXTRA_DEPARTMENT)) {
            val extraData = intent.getParcelableExtra<GetDepartmentModel>(EXTRA_DEPARTMENT)
            viewModel.setDepartmentCode(extraData.departmentCode)
            viewModel.setDepartmentName(extraData.departmentName)
            viewModel.setDepartmentShortName(extraData.departmentShortName)
//            binding.departmentName.setText(extraData.departmentName)
//            binding.departmentShortName.setText(extraData.departmentShortName)
//            binding.departmentCode.setText(extraData.departmentCode)
            viewModel.setDepartmentId(extraData.id)
        }

        viewModel.status.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(Constants.EXTRA_MESSAGE, getString(R.string.update_success))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showToast(getString(R.string.update_failed))
            }
        })

        binding.btnUpdateDepartment.setOnClickListener {
            when {
                viewModel.departmentName.value!!.isEmpty() -> binding.departmentNameLayout.error =
                    getString(R.string.empty_field)
                viewModel.departmentShortName.value!!.isEmpty() -> binding.departmentShortNameLayout.error =
                    getString(R.string.empty_field)
                viewModel.departmentCode.value!!.isEmpty() -> binding.departmentCodeLayout.error =
                    getString(R.string.empty_field)
                else -> viewModel.updateDepartment()
            }
        }

        setupProgressBar()

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
