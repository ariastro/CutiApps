package com.astronout.tmc.modules.employees.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAddEmployeeBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.employees.viewmodel.AddEmployeeViewModel
import com.astronout.tmc.modules.profile.view.ChooseDepartmentActivity
import com.astronout.tmc.modules.profile.view.ChooseDepartmentActivity.Companion.REQUEST_DEPARTMENT
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.dialogDate
import com.astronout.tmc.utils.glide.GlideApp
import com.astronout.tmc.utils.isValidEmail
import com.astronout.tmc.utils.showToast

class AddEmployeeActivity : BaseActivity() {

    private lateinit var binding: ActivityAddEmployeeBinding
    private lateinit var viewModel: AddEmployeeViewModel

    companion object {
        const val REQUEST_ADD_EMPLOYEE = 28213
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_employee)
        viewModel = ViewModelProvider(this).get(AddEmployeeViewModel::class.java)
        binding.addEmployee = viewModel

        setSupportActionBar(binding.toolbar)

        setupProgressBar()

        binding.birthday.setOnClickListener {
            dialogDate(this) { dateResult ->
                viewModel.setDob(dateResult)
            }
        }

        binding.department.setOnClickListener {
            startActivityForResult(
                Intent(this, ChooseDepartmentActivity::class.java),
                REQUEST_DEPARTMENT
            )
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

        viewModel.department.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.department.setText(it)
            }
        })

        binding.btnAdd.setOnClickListener {
            when {
                binding.empId.text.toString().isEmpty() -> binding.empId.error =
                    getString(R.string.empty_field)
                binding.firstName.text.toString().isEmpty() -> binding.firstName.error =
                    getString(R.string.empty_field)
                binding.lastName.text.toString().isEmpty() -> binding.lastName.error =
                    getString(R.string.empty_field)
                binding.email.text.toString().isEmpty() -> binding.email.error =
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
                binding.department.text.toString().isEmpty() -> binding.department.error =
                    getString(R.string.empty_field)
                binding.position.text.toString().isEmpty() -> binding.position.error =
                    getString(R.string.empty_field)
                binding.annualLeaveRights.text.toString().isEmpty() -> binding.annualLeaveRights.error =
                    getString(R.string.empty_field)
                else -> {
                    viewModel.addEmployee()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_DEPARTMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val department = data.getStringExtra(ChooseDepartmentActivity.EXTRA_DEPARTMENT)
                    viewModel.setDepartment(department!!)
                }
            }
        }
    }

}
