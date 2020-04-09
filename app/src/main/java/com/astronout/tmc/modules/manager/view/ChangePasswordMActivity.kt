package com.astronout.tmc.modules.manager.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityChangePasswordMBinding
import com.astronout.tmc.modules.manager.viewmodel.ChangePasswordMViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.showToast

class ChangePasswordMActivity : BaseActivity() {

    private lateinit var binding: ActivityChangePasswordMBinding
    private lateinit var viewModel: ChangePasswordMViewModel

    companion object {
        const val REQUEST_CHANGE_PASSWORD = 29421
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password_m)
        viewModel = ViewModelProvider(this).get(ChangePasswordMViewModel::class.java)
        binding.changePasswordManager = viewModel

        setSupportActionBar(binding.toolbar)

        setupProgressBar()

        showErrorChangePassword()

        viewModel.onComplete.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(Constants.EXTRA_MESSAGE, getString(R.string.change_password_success))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showToast(getString(R.string.change_password_failed))
            }
        })

        binding.btnChange.setOnClickListener {
            when {
                binding.oldPassword.text.toString().isEmpty() -> binding.oldPasswordLayout.error =
                    getString(R.string.empty_password)
                binding.newPassword.text.toString().isEmpty() -> binding.newPasswordLayout.error =
                    getString(R.string.empty_new_password)
                binding.confirmNewPassword.text.toString().isEmpty() -> binding.confirmNewPasswordLayout.error =
                    getString(R.string.confirm_new_password_not_match)
                else -> viewModel.changePassword()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun showErrorChangePassword() {
        viewModel.oldPassword.observe(this, Observer { password ->
            if (password.isEmpty()) {
                binding.oldPasswordLayout.isErrorEnabled = true
                binding.oldPasswordLayout.error = getString(R.string.empty_password)
            } else {
                binding.oldPasswordLayout.isErrorEnabled = false
                binding.oldPasswordLayout.error = null
            }
        })

        viewModel.newPassword.observe(this, Observer { password ->
            if (password.isEmpty()) {
                binding.newPasswordLayout.isErrorEnabled = true
                binding.newPasswordLayout.error = getString(R.string.empty_new_password)
            } else {
                binding.newPasswordLayout.isErrorEnabled = false
                binding.newPasswordLayout.error = null
            }
            viewModel.confirmNewPassword.observe(this, Observer { confirmPassword ->
                if (confirmPassword != password) {
                    binding.confirmNewPasswordLayout.isErrorEnabled = true
                    binding.confirmNewPasswordLayout.error = getString(R.string.confirm_new_password_not_match)
                } else {
                    binding.confirmNewPasswordLayout.isErrorEnabled = false
                    binding.confirmNewPasswordLayout.error = null
                }
            })
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupProgressBar(){
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                progress.show()
            } else {
                progress.dismiss()
            }
        })
    }

}