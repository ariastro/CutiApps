package com.astronout.tmc.modules.leavetype.annual.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityUpdateAnnualBinding
import com.astronout.tmc.modules.leavetype.annual.model.GetAnnualModel
import com.astronout.tmc.modules.leavetype.annual.viewmodel.UpdateAnnualViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class UpdateAnnualActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateAnnualBinding
    private lateinit var viewModel: UpdateAnnualViewModel

    companion object {
        const val REQUEST_UPDATE_ANNUAL = 6191
        const val EXTRA_ANNUAL = "EXTRA_ANNUAL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_annual)
        viewModel = ViewModelProvider(this).get(UpdateAnnualViewModel::class.java)
        binding.updateAnnual = viewModel

        setSupportActionBar(binding.toolbar)

        if (intent.hasExtra(EXTRA_ANNUAL)) {
            val extraData = intent.getParcelableExtra<GetAnnualModel>(EXTRA_ANNUAL)
            viewModel.setId(extraData.id)
            viewModel.setDescription(extraData.description)
            viewModel.setNo(extraData.no)
            viewModel.setLeaveType(extraData.leaveType)
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

        binding.btnUpdate.setOnClickListener {
            when {
                viewModel.no.value!!.isEmpty() -> binding.annualNoLayout.error =
                    getString(R.string.empty_field)
                viewModel.description.value!!.isEmpty() -> binding.annualDescriptionLayout.error =
                    getString(R.string.empty_field)
                viewModel.leaveType.value!!.isEmpty() -> binding.annualLeaveTypeLayout.error =
                    getString(R.string.empty_field)
                else -> viewModel.updateAnnual()
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