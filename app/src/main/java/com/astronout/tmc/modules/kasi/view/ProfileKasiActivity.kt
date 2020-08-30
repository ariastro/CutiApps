package com.astronout.tmc.modules.kasi.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.BuildConfig
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityProfileKasiBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.kasi.viewmodel.ProfileKasiViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.Constants.USER_KASI
import com.astronout.tmc.utils.glide.GlideApp
import com.astronout.tmc.utils.showToast

class ProfileKasiActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileKasiBinding
    private lateinit var viewModel: ProfileKasiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_kasi)
        viewModel = ViewModelProvider(this).get(ProfileKasiViewModel::class.java)

        setSupportActionBar(binding.toolbar)

        viewModel.kasi.observe(this, Observer {
            if (it != null) {
                binding.fullname.text = it.kasiName
                binding.username.text = it.kasiUsername
                binding.gender.text = it.kasiGender
                binding.dob.text = it.kasiBirthday
                binding.phoneNumber.text = it.kasiPhone
                binding.address.text = it.kasiAddress
                binding.city.text = it.kasiCity
                if (User.userType == USER_KASI) {
                    binding.jabatan.text = "Kasi " + it.kasiJabatan
                } else {
                    binding.jabatan.text = "Kasubag " + it.kasiJabatan
                }
                binding.country.text = it.kasiCountry

                if (it.kasiStatus == Constants.STATUS_AKUN_AKTIF) {
                    binding.status.text = getString(R.string.aktif)
                } else {
                    binding.status.text = getString(R.string.non_aktif)
                }

                if (it.kasiAvatar.isNullOrEmpty()) {
                    if (it.kasiAvatar == Constants.PRIA) {
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
                        .load(BuildConfig.BASE_IMG_AVATAR + it.kasiAvatar)
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
            val intent = Intent(this, UpdateProfileKActivity::class.java)
            intent.putExtra(UpdateProfileKActivity.EXTRA_UPDATE_KASI, viewModel.kasi.value!!)
            startActivityForResult(intent, UpdateProfileKActivity.REQUEST_UPDATE_PROFILE)
        } else if (item.itemId == R.id.change_password) {
            startActivityForResult(Intent(this, ChangePasswordKActivity::class.java),
                ChangePasswordKActivity.REQUEST_CHANGE_PASSWORD)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UpdateProfileKActivity.REQUEST_UPDATE_PROFILE) {
            viewModel.getProfile()
        } else if (requestCode == ChangePasswordKActivity.REQUEST_CHANGE_PASSWORD) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
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