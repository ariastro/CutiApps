package com.astronout.tmc.modules.leavetype.nonannual.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityUpdateNonAnnualBinding
import com.astronout.tmc.modules.leavetype.nonannual.model.GetNonAnnualModel
import com.astronout.tmc.modules.leavetype.nonannual.viewmodel.UpdateNonAnnualViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class UpdateNonAnnualActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateNonAnnualBinding
    private lateinit var viewModel: UpdateNonAnnualViewModel

    companion object {
        const val REQUEST_UPDATE_NON_ANNUAL = 6191
        const val EXTRA_NON_ANNUAL = "EXTRA_NON_ANNUAL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_non_annual)
        viewModel = ViewModelProvider(this).get(UpdateNonAnnualViewModel::class.java)
        binding.updateNonAnnual = viewModel

        setSupportActionBar(binding.toolbar)

        if (intent.hasExtra(EXTRA_NON_ANNUAL)) {
            val extraData = intent.getParcelableExtra<GetNonAnnualModel>(EXTRA_NON_ANNUAL)
            viewModel.setId(extraData.id)
            viewModel.setDescription(extraData.description)
            viewModel.setNo(extraData.no)
            viewModel.setLeaveType(extraData.leaveType)
            viewModel.setRightsGranted(extraData.rightsGranted)
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
                viewModel.rightsGranted.value!!.isEmpty() -> binding.rightsGrantedLayout.error =
                    getString(R.string.empty_field)
                viewModel.leaveType.value!!.isEmpty() -> binding.annualLeaveTypeLayout.error =
                    getString(R.string.empty_field)
                else -> viewModel.updateNonAnnual()
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