package com.astronout.tmc.modules.leavetype.annual.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAddAnnualBinding
import com.astronout.tmc.modules.leavetype.annual.viewmodel.AddAnnualViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class AddAnnualActivity : BaseActivity() {

    private lateinit var binding: ActivityAddAnnualBinding
    private lateinit var viewModel: AddAnnualViewModel

    companion object {
        const val REQUEST_ADD_ANNUAL = 9113
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_annual)
        viewModel = ViewModelProvider(this).get(AddAnnualViewModel::class.java)
        binding.addAnnual = viewModel

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
                viewModel.description.value!!.isEmpty() -> binding.annualDescriptionLayout.error =
                    getString(R.string.empty_field)
                else -> viewModel.addAnnual()
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
