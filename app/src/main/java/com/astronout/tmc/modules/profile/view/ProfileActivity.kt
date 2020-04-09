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

        viewModel.empId.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.empId.text = it
            }
        })

        viewModel.firstname.observe(this, Observer {firstName ->
            if (firstName.isNotEmpty()) {
                viewModel.lastname.observe(this, Observer { lastName ->
                    if (lastName.isNotEmpty()) {
                        binding.fullname.text = "$firstName $lastName"
                    }
                })
            }
        })

        viewModel.gender.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.gender.text = it
//                if (it == PRIA) {
//                    GlideApp.with(this)
//                        .load(R.drawable.avatar_male)
//                        .into(binding.profilePicture)
//                } else {
//                    GlideApp.with(this)
//                        .load(R.drawable.avatar_female)
//                        .into(binding.profilePicture)
//                }
            }
        })

        viewModel.avatar.observe(this, Observer {
            if (it != "") {
                GlideApp.with(this)
                    .load(BuildConfig.BASE_IMG_AVATAR + it)
                    .into(binding.profilePicture)
            } else {
                if (viewModel.gender.value == PRIA) {
                    GlideApp.with(this)
                        .load(R.drawable.avatar_male)
                        .into(binding.profilePicture)
                } else {
                    GlideApp.with(this)
                        .load(R.drawable.avatar_female)
                        .into(binding.profilePicture)
                }
            }
        })

        viewModel.email.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.email.text = it
            }
        })

        viewModel.position.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.position.text = it
            }
        })

        viewModel.department.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.department.text = it
            }
        })

        viewModel.dob.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.dob.text = it
            }
        })

        viewModel.address.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.address.text = it
            }
        })

        viewModel.city.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.city.text = it
            }
        })

        viewModel.country.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.country.text = it
            }
        })

        viewModel.annualLeaveRights.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.annualLeaveRights.text = "$it hari"
            }
        })

        viewModel.phoneNumber.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.phoneNumber.text = it
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
