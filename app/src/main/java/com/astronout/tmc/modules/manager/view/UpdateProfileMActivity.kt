package com.astronout.tmc.modules.manager.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.BuildConfig
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityUpdateProfileMBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.manager.model.GetProfileManagerModel
import com.astronout.tmc.modules.manager.viewmodel.UpdateProfileMViewModel
import com.astronout.tmc.utils.Constants
import com.astronout.tmc.utils.dialogDate
import com.astronout.tmc.utils.glide.GlideApp
import com.astronout.tmc.utils.imagePicker
import com.astronout.tmc.utils.showToast
import com.esafirm.imagepicker.features.ImagePicker

class UpdateProfileMActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateProfileMBinding
    private lateinit var viewModel: UpdateProfileMViewModel

    private var path: String = ""

    companion object {
        val REQUEST_UPDATE_PROFILE = 2291
        val EXTRA_UPDATE_MANAGER = "EXTRA_UPDATE_MANAGER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile_m)
        viewModel = ViewModelProvider(this).get(UpdateProfileMViewModel::class.java)
        binding.updateProfileManager = viewModel

        setSupportActionBar(binding.toolbar)

        binding.birthday.setOnClickListener {
            dialogDate(this) { dateResult ->
                viewModel.setDob(dateResult)
            }
        }

        val extraData = intent.getParcelableExtra<GetProfileManagerModel>(EXTRA_UPDATE_MANAGER)

        if (extraData.managerGender == Constants.PRIA) {
            binding.radioMale.isChecked = true
            viewModel.setGender(Constants.PRIA)
        } else {
            binding.radioFemale.isChecked = true
            viewModel.setGender(Constants.WANITA)
        }

        viewModel.setUsername(extraData.managerUsername)
        viewModel.setName(extraData.managerName)
        viewModel.setAddress(extraData.managerAddress)
        viewModel.setPhoneNumber(extraData.managerPhone)
        viewModel.setCity(extraData.managerCity)
        viewModel.setCountry(extraData.managerCountry)
        viewModel.setDob(extraData.managerBirthday)

        if (extraData.managerAvatar != "") {
            GlideApp.with(this)
                .load(BuildConfig.BASE_IMG_AVATAR + extraData.managerAvatar)
                .into(binding.profileAvatar)
        } else {
            if (extraData.managerGender == Constants.PRIA) {
                GlideApp.with(this)
                    .load(R.drawable.avatar_male)
                    .into(binding.profileAvatar)
            } else {
                GlideApp.with(this)
                    .load(R.drawable.avatar_female)
                    .into(binding.profileAvatar)
            }
        }

        viewModel.avatarMessage.observe(this, Observer {
            if (it.isNotEmpty()) {
                showToast(it)
            }
        })

        viewModel.avatarUploaded.observe(this, Observer {
            if (it == true) {
                binding.profileAvatar.setImageBitmap(BitmapFactory.decodeFile(path))
            }
        })

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
                intent.putExtra(Constants.EXTRA_MESSAGE, getString(R.string.update_profile_success))
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                showToast(getString(R.string.failed_update_profile))
            }
        })

        viewModel.dob.observe(this, Observer {
            if (it != null) {
                binding.birthday.setText(it)
            }
        })

        binding.btnUpdate.setOnClickListener {
            when {
                binding.username.text.toString().isEmpty() -> binding.username.error =
                    getString(R.string.empty_field)
                binding.name.text.toString().isEmpty() -> binding.name.error =
                    getString(R.string.empty_field)
                binding.phone.text.toString().isEmpty() -> binding.phone.error =
                    getString(R.string.empty_field)
                binding.address.text.toString().isEmpty() -> binding.address.error =
                    getString(R.string.empty_field)
                binding.city.text.toString().isEmpty() -> binding.city.error =
                    getString(R.string.empty_field)
                binding.country.text.toString().isEmpty() -> binding.country.error =
                    getString(R.string.empty_field)
                else -> {
                    viewModel.updateProfile()
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
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)
            path = image.path
            viewModel.setImagePath(path)
            viewModel.uploadImage()
//            binding.profileAvatar.setImageBitmap(BitmapFactory.decodeFile(path))
        }
    }

    private fun pickImageFromGallery() {
        imagePicker()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_camera, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_camera -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED
                    ) {
                        /*permission denied*/
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        /*show popup to request runtime permission*/
                        requestPermissions(permissions, Constants.PERMISSION_CODE)
                    } else {
                        /*permission already granted*/
                        pickImageFromGallery()
                    }
                } else {
                    /*system OS is < Marshmallow*/
                    pickImageFromGallery()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}