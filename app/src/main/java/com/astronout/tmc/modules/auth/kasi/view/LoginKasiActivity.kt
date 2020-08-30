package com.astronout.tmc.modules.auth.kasi.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityLoginKasiBinding
import com.astronout.tmc.modules.auth.admin.view.LoginAdminActivity
import com.astronout.tmc.modules.auth.empolyees.view.LoginActivity
import com.astronout.tmc.modules.auth.kasi.viewmodel.LoginKasiViewModel
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.auth.manager.view.LoginManagerActivity
import com.astronout.tmc.modules.main.KasiHomeActivity
import com.astronout.tmc.utils.Constants.STATUS_AKUN_AKTIF
import com.astronout.tmc.utils.showToast

class LoginKasiActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginKasiBinding
    private lateinit var viewModel: LoginKasiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_kasi)
        viewModel = ViewModelProvider(this).get(LoginKasiViewModel::class.java)
        binding.loginKasi = viewModel

//        showErrorLogin()

        binding.loginUsername.addTextChangedListener(onUsernameChange())
        binding.loginPassword.addTextChangedListener(onPasswordChange())

        binding.btnLogin.setOnClickListener {
            when {
                binding.loginUsername.text.toString().isEmpty() -> binding.loginUsername.error =
                    getString(R.string.empty_username)
                binding.loginPassword.text.toString().isEmpty() -> binding.loginPassword.error =
                    getString(R.string.empty_password)
                else -> viewModel.loginKasi()
            }
        }

        binding.tvLoginKaryawan.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.tvLoginManager.setOnClickListener {
            startActivity(Intent(this, LoginManagerActivity::class.java))
            finish()
        }

        binding.tvLoginAdmin.setOnClickListener {
            startActivity(Intent(this, LoginAdminActivity::class.java))
            finish()
        }

        viewModel.isSucceed.observe(this, Observer {
            if (it) {
                if (User.kasiStatus == STATUS_AKUN_AKTIF) {
                    startActivity(Intent(this, KasiHomeActivity::class.java))
                    finish()
                } else {
                    showToast(getString(R.string.status_non_aktif))
                }
            } else {
                viewModel.message.observe(this, Observer { message ->
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
                viewModel.setUserName(binding.loginUsername.text.toString())
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
                viewModel.setPassword(binding.loginPassword.text.toString())
            }

        }
    }

    private fun showErrorLogin() {
        viewModel.userName.observe(this, Observer {
            when {
                it.isEmpty() -> {
                    binding.loginUsername.error = getString(R.string.empty_username)
                }
                else -> {
                    binding.loginUsername.error = null
                }
            }
        })

        viewModel.password.observe(this, Observer {
            when {
                it.isEmpty() -> {
                    binding.loginPassword.error = getString(R.string.empty_password)
                }
                else -> {
                    binding.loginPassword.error = null
                }
            }
        })
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

}