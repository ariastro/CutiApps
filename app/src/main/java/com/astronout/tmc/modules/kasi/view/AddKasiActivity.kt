package com.astronout.tmc.modules.kasi.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAddKasiBinding
import com.astronout.tmc.modules.kasi.viewmodel.AddKasiViewModel
import com.astronout.tmc.modules.profile.view.ChooseDepartmentActivity
import com.astronout.tmc.modules.profile.view.ChooseDepartmentActivity.Companion.REQUEST_DEPARTMENT
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.Constants.USER_KASI
import com.astronout.tmc.utils.Constants.USER_KSB
import com.astronout.tmc.utils.dialogDate
import com.astronout.tmc.utils.showToast

class AddKasiActivity : BaseActivity() {

    private lateinit var binding: ActivityAddKasiBinding
    private lateinit var viewModel: AddKasiViewModel

    companion object {
        const val REQUEST_ADD_KASI = 803
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_kasi)
        viewModel = ViewModelProvider(this).get(AddKasiViewModel::class.java)
        binding.addKasi = viewModel

        setSupportActionBar(binding.toolbar)

        setupProgressBar()

        binding.birthday.setOnClickListener {
            dialogDate(this) { dateResult ->
                viewModel.setDob(dateResult)
            }
        }

        binding.jabatan.setOnClickListener {
            startActivityForResult(Intent(this, ChooseDepartmentActivity::class.java), REQUEST_DEPARTMENT)
        }

        binding.radioGender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioMale) {
                viewModel.setGender(Constants.PRIA)
            } else {
                viewModel.setGender(Constants.WANITA)
            }
        }

        binding.radioJenis.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioKasi) {
                viewModel.setjenis(USER_KASI)
            } else {
                viewModel.setjenis(USER_KSB)
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

        viewModel.jabatan.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.jabatan.setText(it)
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
                binding.jabatan.text.toString().isEmpty() -> binding.jabatan.error =
                    getString(R.string.empty_field)
                else -> {
                    viewModel.addKasi()
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
                    val jabatan = data.getStringExtra(ChooseDepartmentActivity.EXTRA_DEPARTMENT)
                    viewModel.setJabatan(jabatan!!)
                }
            }
        }
    }

}