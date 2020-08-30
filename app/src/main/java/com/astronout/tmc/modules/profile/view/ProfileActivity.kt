package com.astronout.tmc.modules.profile.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.BuildConfig
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityProfileBinding
import com.astronout.tmc.modules.profile.view.ChangePasswordActivity.Companion.REQUEST_CHANGE_PASSWORD
import com.astronout.tmc.modules.profile.view.UpdateProfileActivity.Companion.REQUEST_UPDATE_PROFILE
import com.astronout.tmc.modules.profile.viewmodel.ProfileViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import com.astronout.tmc.utils.Constants.PRIA
import com.astronout.tmc.utils.glide.GlideApp
import com.astronout.tmc.utils.showToast

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.profile = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.getProfile()

        viewModel.userProfile.observe(this, Observer {
            if (it != null) {
                binding.fullname.text = it.firstName + it.lastName
                binding.empId.text = "NIP: ${it.empId}"
                binding.username.text = it.emailId
                binding.department.text = it.department
                binding.position.text = it.position
                binding.gender.text = it.gender
                binding.dob.text = it.dob
                binding.phoneNumber.text = it.phonenumber
                binding.address.text = it.address
                binding.city.text = it.city
                binding.country.text = it.country
                binding.annualLeaveRights.text = it.annualLeaveRights

                if (it.avatar.isNullOrEmpty()) {
                    if (it.gender == PRIA) {
                        GlideApp.with(this)
                            .load(R.drawable.avatar_male)
                            .into(binding.profilePicture)
                    } else {
                        GlideApp.with(this)
                            .load(R.drawable.avatar_female)
                            .into(binding.profilePicture)
                    }
                } else {
                    GlideApp.with(this)
                        .load(BuildConfig.BASE_IMG_AVATAR + it.avatar)
                        .into(binding.profilePicture)
                }

            }
        })

        setupProgressBar()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update_profile){
            startActivityForResult(Intent(this, UpdateProfileActivity::class.java),
                REQUEST_UPDATE_PROFILE)
        } else if (item.itemId == R.id.change_password) {
            startActivityForResult(Intent(this, ChangePasswordActivity::class.java),
                REQUEST_CHANGE_PASSWORD)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_UPDATE_PROFILE) {
//            if (resultCode == Activity.RESULT_OK) {
//                if (data != null) {
                    viewModel.getProfile()
//                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
//                }
//            }
        } else if (requestCode == REQUEST_CHANGE_PASSWORD) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    viewModel.getProfile()
                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}
