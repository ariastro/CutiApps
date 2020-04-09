package com.astronout.tmc.modules.manager.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAddManagerBinding
import com.astronout.tmc.modules.manager.viewmodel.AddmanagerViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.dialogDate
import com.astronout.tmc.utils.showToast

class AddManagerActivity : BaseActivity() {

    private lateinit var binding: ActivityAddManagerBinding
    private lateinit var viewModel: AddmanagerViewModel

    companion object {
        const val REQUEST_ADD_MANAGER = 11282
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_manager)
        viewModel = ViewModelProvider(this).get(AddmanagerViewModel::class.java)
        binding.addManager = viewModel

        setSupportActionBar(binding.toolbar)

        setupProgressBar()

        binding.birthday.setOnClickListener {
            dialogDate(this) { dateResult ->
                viewModel.setDob(dateResult)
            }
        }

        binding.radioGender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioMale) {
                viewModel.setGender(Constants.PRIA)
            } else {
                viewModel.setGender(Constants.WANITA)
            }
        }

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

        viewModel.dob.observe(this, Observer {
            if (it != null) {
                binding.birthday.setText(it)
            }
        })

        binding.btnAdd.setOnClickListener {
            when {
                binding.name.text.toString().isEmpty() -> binding.name.error =
                    getString(R.string.empty_field)
                binding.username.text.toString().isEmpty() -> binding.username.error =
                    getString(R.string.empty_field)
                binding.password.text.toString().isEmpty() -> binding.password.error =
                    getString(R.string.empty_field)
                binding.phone.text.toString().isEmpty() -> binding.phone.error =
                    getString(R.string.empty_field)
                binding.address.text.toString().isEmpty() -> binding.address.error =
                    getString(R.string.empty_field)
                binding.city.text.toString().isEmpty() -> binding.city.error =
                    getString(R.string.empty_field)
                binding.country.text.toString().isEmpty() -> binding.country.error =
                    getString(R.string.empty_field)
                binding.birthday.text.toString().isEmpty() -> binding.birthday.error =
                    getString(R.string.empty_field)
                else -> {
                    viewModel.addManager()
                }
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