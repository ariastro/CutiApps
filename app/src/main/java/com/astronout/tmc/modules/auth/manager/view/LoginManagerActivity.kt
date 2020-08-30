package com.astronout.tmc.modules.auth.manager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityLoginManagerBinding
import com.astronout.tmc.modules.auth.admin.view.LoginAdminActivity
import com.astronout.tmc.modules.auth.empolyees.view.LoginActivity
import com.astronout.tmc.modules.auth.kasi.view.LoginKasiActivity
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.auth.manager.viewmodel.LoginManagerViewModel
import com.astronout.tmc.modules.main.AdminHomeActivity
import com.astronout.tmc.modules.main.ManagerHomeActivity
import com.astronout.tmc.utils.showToast

class LoginManagerActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginManagerBinding
    private lateinit var viewModel: LoginManagerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_manager)
        viewModel = ViewModelProvider(this).get(LoginManagerViewModel::class.java)
        binding.loginManager = viewModel

//        showErrorLogin()

        binding.loginUserName.addTextChangedListener(onUsernameChange())
        binding.loginManagerPassword.addTextChangedListener(onPasswordChange())

        binding.btnLoginManager.setOnClickListener {
            when {
                binding.loginUserName.text.toString().isEmpty() -> binding.loginUserName.error =
                    getString(R.string.empty_username)
                binding.loginManagerPassword.text.toString().isEmpty() -> binding.loginManagerPassword.error =
                    getString(R.string.empty_password)
                else -> viewModel.loginManager()
            }
        }

        binding.tvLoginKaryawan.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.tvLoginAdmin.setOnClickListener {
            startActivity(Intent(this, LoginAdminActivity::class.java))
            finish()
        }

        binding.tvLoginKasi.setOnClickListener {
            startActivity(Intent(this, LoginKasiActivity::class.java))
            finish()
        }

        viewModel.isSucceed.observe(this, Observer {
            if (it) {
                if (User.managerStatus == "1") {
                    startActivity(Intent(this, ManagerHomeActivity::class.java))
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
                viewModel.setPassword(binding.loginManagerPassword.text.toString())
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
                    binding.loginManagerPassword.error = getString(R.string.empty_password)
                }
                else -> {
                    binding.loginManagerPassword.error = null
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