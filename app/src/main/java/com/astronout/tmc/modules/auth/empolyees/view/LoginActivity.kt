package com.astronout.tmc.modules.auth.empolyees.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityLoginBinding
import com.astronout.tmc.modules.auth.admin.view.LoginAdminActivity
import com.astronout.tmc.modules.auth.empolyees.viewmodel.LoginViewModel
import com.astronout.tmc.modules.auth.kasi.view.LoginKasiActivity
import com.astronout.tmc.modules.auth.manager.view.LoginManagerActivity
import com.astronout.tmc.modules.main.MainActivity
import com.astronout.tmc.utils.isValidEmail
import com.astronout.tmc.utils.showToast

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.login = viewModel

        viewModel.isLogedIn.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                showToast(getString(R.string.incorrect_login_info))
            }
        })

        binding.btnLogin.setOnClickListener {
            when {
                binding.loginEmail.text.toString().isEmpty() ->
                    binding.loginEmail.error = getString(R.string.empty_username)
                binding.loginPassword.text.toString()
                    .isEmpty() -> binding.loginPassword.error = getString(R.string.empty_password)
                else -> viewModel.loginEmployees()
            }
        }

        binding.tvLoginAdmin.setOnClickListener {
            startActivity(Intent(this, LoginAdminActivity::class.java))
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

        setupProgressBar()

    }

    private fun setupProgressBar() {
        viewModel.isLoading.observe(this, Observer {
            if (it)
                progress.show()
            else
                progress.dismiss()
        })
    }

}
