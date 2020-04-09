package com.astronout.tmc.modules.leavetype.nonannual.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAddNonAnnualBinding
import com.astronout.tmc.modules.leavetype.nonannual.viewmodel.AddNonAnnualViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class AddNonAnnualActivity : BaseActivity() {

    private lateinit var binding: ActivityAddNonAnnualBinding
    private lateinit var viewModel: AddNonAnnualViewModel

    companion object {
        const val REQUEST_ADD_NON_ANNUAL = 1104
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_non_annual)
        viewModel = ViewModelProvider(this).get(AddNonAnnualViewModel::class.java)
        binding.addNonAnnual = viewModel

        setSupportActionBar(binding.toolbar)

        setupProgressBar()

        viewModel.status.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(Constants.EXTRA_MESSAGE, getString(R.string.add_success))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showToast(getString(R.string.add_failed))
            }
        })

        binding.btnAdd.setOnClickListener {
            when {
                viewModel.no.value!!.isEmpty() -> binding.annualNoLayout.error =
                    getString(R.string.empty_field)
                viewModel.leaveType.value!!.isEmpty() -> binding.annualLeaveTypeLayout.error =
                    getString(R.string.empty_field)
                viewModel.rightsGranted.value!!.isEmpty() -> binding.rightsGrantedLayout.error =
                    getString(R.string.empty_field)
                viewModel.description.value!!.isEmpty() -> binding.annualDescriptionLayout.error =
                    getString(R.string.empty_field)
                else -> viewModel.addNonAnnual()
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
