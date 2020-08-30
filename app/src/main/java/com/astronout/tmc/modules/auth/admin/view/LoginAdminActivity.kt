package com.astronout.tmc.modules.auth.admin.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityLoginAdminBinding
import com.astronout.tmc.modules.main.AdminHomeActivity
import com.astronout.tmc.modules.auth.admin.viewmodel.LoginAdminViewModel
import com.astronout.tmc.modules.auth.empolyees.view.LoginActivity
import com.astronout.tmc.modules.auth.kasi.view.LoginKasiActivity
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.auth.manager.view.LoginManagerActivity
import com.astronout.tmc.utils.showToast

class LoginAdminActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginAdminBinding
    private lateinit var viewModel: LoginAdminViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_admin)
        viewModel = ViewModelProvider(this).get(LoginAdminViewModel::class.java)
        binding.loginAdmin = viewModel

//        showErrorLogin()

        binding.loginUserName.addTextChangedListener(onUsernameChange())
        binding.loginAdminPassword.addTextChangedListener(onPasswordChange())

        binding.btnLoginAdmin.setOnClickListener {
            when {
                binding.loginUserName.text.toString().isEmpty() -> binding.loginUserName.error = getString(R.string.empty_username)
                binding.loginAdminPassword.text.toString().isEmpty() -> binding.loginAdminPassword.error = getString(R.string.empty_password)
                else -> viewModel.loginAdmin()
            }
        }

//        binding.btnLoginManajer.setOnClickListener {
//            when {
//                binding.loginUserName.text.toString().isEmpty() -> binding.loginUserName.error = getString(R.string.empty_username)
//                binding.loginAdminPassword.text.toString().isEmpty() -> binding.loginAdminPassword.error = getString(R.string.empty_password)
//                else -> viewModel.loginManager()
//            }
//        }

        binding.tvLoginKaryawan.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.tvLoginManager.setOnClickListener {
            startActivity(Intent(this, LoginManagerActivity::class.java))
            finish()
        }

        binding.tvLoginKasi.setOnClickListener {
            startActivity(Intent(this, LoginKasiActivity::class.java))
            finish()
        }

        viewModel.isSucceed.observe(this, Observer {
            if (it) {
                if (User.adminStatus == "1") {
                    startActivity(Intent(this, AdminHomeActivity::class.java))
                    finish()
                } else {
                    showToast(getString(R.string.status_non_aktif))
                }
            } else {
                viewModel.message.observe(this, Observer {message ->
                    showToast(message)
                })
            }
        })

        setupProgressBar()

    }

    private fun onUsernameChange(): TextWatcher? {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setUserName(binding.loginUserName.text.toString())
            }

        }
    }

    private fun onPasswordChange(): TextWatcher? {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setPassword(binding.loginAdminPassword.text.toString())
            }

        }
    }

    private fun showErrorLogin() {
        viewModel.userName.observe(this, Observer {
            when {
                it.isEmpty() -> {
                    binding.loginUserName.error = getString(R.string.empty_username)
                }
                else -> {
                    binding.loginUserName.error = null
                }
            }
        })

        viewModel.password.observe(this, Observer {
            when {
                it.isEmpty() -> {
                    binding.loginAdminPassword.error = getString(R.string.empty_password)
                }
                else -> {
                    binding.loginAdminPassword.error = null
                }
            }
        })
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
