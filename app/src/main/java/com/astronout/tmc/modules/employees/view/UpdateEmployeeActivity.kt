package com.astronout.tmc.modules.employees.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityUpdateEmployeeBinding
import com.astronout.tmc.modules.employees.model.GetAllEmployeesModel
import com.astronout.tmc.modules.employees.viewmodel.UpdateEmployeeViewModel
import com.astronout.tmc.modules.profile.view.ChooseDepartmentActivity
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.dialogDate
import com.astronout.tmc.utils.isValidEmail
import com.astronout.tmc.utils.showToast

class UpdateEmployeeActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateEmployeeBinding
    private lateinit var viewModel: UpdateEmployeeViewModel

    companion object {
        const val REQUEST_UPDATE_EMPLOYEE = 3029
        const val EXTRA_EMPLOYEE = "EXTRA_EMPLOYEE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_employee)
        viewModel = ViewModelProvider(this).get(UpdateEmployeeViewModel::class.java)
        binding.updateEmployee = viewModel

        setSupportActionBar(binding.toolbar)

        if (intent.hasExtra(EXTRA_EMPLOYEE)) {
            val extraData = intent.getParcelableExtra<GetAllEmployeesModel>(EXTRA_EMPLOYEE)
            viewModel.setEmpId(extraData.empId)
            viewModel.setFirstname(extraData.firstName)
            viewModel.setLastname(extraData.lastName)
            viewModel.setEmail(extraData.emailId)
            viewModel.setAddress(extraData.address)
            viewModel.setCity(extraData.city)
            viewModel.setCountry(extraData.country)
            viewModel.setGender(extraData.gender)
            viewModel.setPhoneNumber(extraData.phonenumber)
            viewModel.setDob(extraData.dob)
            viewModel.setDepartment(extraData.department)
            viewModel.setPosition(extraData.position)
            viewModel.setAnnualLeaveRights(extraData.annualLeaveRights)
            viewModel.setId(extraData.id)
            binding.birthday.setText(extraData.dob)
            binding.department.setText(extraData.department)
            if (extraData.gender == Constants.PRIA) {
                binding.radioMale.isChecked = true
            } else {
                binding.radioFemale.isChecked = true
            }
        }

        binding.birthday.setOnClickListener {
            dialogDate(this) { dateResult ->
                viewModel.setDob(dateResult)
            }
        }

        binding.department.setOnClickListener {
            startActivityForResult(Intent(this, ChooseDepartmentActivity::class.java),
                ChooseDepartmentActivity.REQUEST_DEPARTMENT
            )
        }

        binding.radioGender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioMale) {
                viewModel.setGender(Constants.PRIA)
            } else {
                viewModel.setGender(Constants.WANITA)
            }
        }

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
                binding.empId.text.toString().isEmpty() -> binding.empId.error =
                    getString(R.string.empty_field)
                binding.firstName.text.toString().isEmpty() -> binding.firstName.error =
                    getString(R.string.empty_field)
                binding.lastName.text.toString().isEmpty() -> binding.lastName.error =
                    getString(R.string.empty_field)
                binding.email.text.toString().isEmpty() -> binding.email.error =
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
                    viewModel.updateEmployee()
                }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ChooseDepartmentActivity.REQUEST_DEPARTMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val department = data.getStringExtra(ChooseDepartmentActivity.EXTRA_DEPARTMENT)
                    viewModel.setDepartment(department!!)
                }
            }
        }
    }

}