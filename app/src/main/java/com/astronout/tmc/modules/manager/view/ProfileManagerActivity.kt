package com.astronout.tmc.modules.manager.view

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
import com.astronout.tmc.databinding.ActivityProfileManagerBinding
import com.astronout.tmc.modules.manager.view.UpdateProfileMActivity.Companion.EXTRA_UPDATE_MANAGER
import com.astronout.tmc.modules.manager.viewmodel.ProfileManagerViewModel
import com.astronout.tmc.modules.profile.view.UpdateProfileActivity.Companion.REQUEST_UPDATE_PROFILE
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.Constants.STATUS_AKUN_AKTIF
import com.astronout.tmc.utils.glide.GlideApp
import com.astronout.tmc.utils.showToast

class ProfileManagerActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileManagerBinding
    private lateinit var viewModel: ProfileManagerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_manager)
        viewModel = ViewModelProvider(this).get(ProfileManagerViewModel::class.java)
        binding.profileManager = viewModel

        setSupportActionBar(binding.toolbar)

        viewModel.managerModel.observe(this, Observer {
            if (it != null) {
                binding.fullname.text = it.managerName
                binding.username.text = it.managerUsername
                binding.gender.text = it.managerGender
                binding.dob.text = it.managerBirthday
                binding.phoneNumber.text = it.managerPhone
                binding.address.text = it.managerAddress
                binding.city.text = it.managerCity
                binding.country.text = it.managerCountry

                if (it.managerStatus == STATUS_AKUN_AKTIF) {
                    binding.status.text = getString(R.string.aktif)
                } else {
                    binding.status.text = getString(R.string.non_aktif)
                }

                if (it.managerAvatar.isNullOrEmpty()) {
                    if (it.managerGender == Constants.PRIA) {
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
                        .load(BuildConfig.BASE_IMG_AVATAR + it.managerAvatar)
                        .into(binding.profilePicture)
                }
            }
        })

        viewModel.getProfile()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update_profile){
            val intent = Intent(this, UpdateProfileMActivity::class.java)
            intent.putExtra(EXTRA_UPDATE_MANAGER, viewModel.managerModel.value!!)
            startActivityForResult(intent, UpdateProfileMActivity.REQUEST_UPDATE_PROFILE)
        } else if (item.itemId == R.id.change_password) { startActivityForResult(
                Intent(this, ChangePasswordMActivity::class.java),
                ChangePasswordMActivity.REQUEST_CHANGE_PASSWORD)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UpdateProfileMActivity.REQUEST_UPDATE_PROFILE) {
//            if (resultCode == Activity.RESULT_OK) {
//                if (data != null) {
            viewModel.getProfile()
//                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
//                }
//            }
        } else if (requestCode == ChangePasswordMActivity.REQUEST_CHANGE_PASSWORD) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
//                    viewModel.getProfile()
                    showToast(data.getStringExtra(Constants.EXTRA_MESSAGE)!!)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
