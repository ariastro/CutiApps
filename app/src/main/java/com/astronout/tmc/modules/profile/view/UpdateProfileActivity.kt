package com.astronout.tmc.modules.profile.view

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
import com.astronout.tmc.databinding.ActivityUpdateProfileBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.profile.view.ChooseDepartmentActivity.Companion.EXTRA_DEPARTMENT
import com.astronout.tmc.modules.profile.view.ChooseDepartmentActivity.Companion.REQUEST_DEPARTMENT
import com.astronout.tmc.modules.profile.viewmodel.UpdateProfileViewModel
import com.astronout.tmc.utils.*
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import com.astronout.tmc.utils.Constants.PRIA
import com.astronout.tmc.utils.Constants.WANITA
import com.astronout.tmc.utils.glide.GlideApp
import com.bumptech.glide.GenericTransitionOptions
import com.esafirm.imagepicker.features.ImagePicker
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class UpdateProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var viewModel: UpdateProfileViewModel

    private var path: String = ""

    companion object {
        val REQUEST_UPDATE_PROFILE = 2491
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile)
        viewModel = ViewModelProvider(this).get(UpdateProfileViewModel::class.java)
        binding.updateProfile = viewModel

        setSupportActionBar(binding.toolbar)

        binding.birthday.setOnClickListener {
            dialogDate(this) { dateResult ->
                viewModel.setDob(dateResult)
            }
        }

        binding.department.setOnClickListener {
            startActivityForResult(Intent(this, ChooseDepartmentActivity::class.java), REQUEST_DEPARTMENT)
        }

        if (User.gender == PRIA) {
            binding.radioMale.isChecked = true
        } else {
            binding.radioFemale.isChecked = true
        }

        if (User.userAvatar != "") {
            GlideApp.with(this)
                .load(BuildConfig.BASE_IMG_AVATAR + User.userAvatar)
                .into(binding.profileAvatar)
        } else {
            if (User.gender == PRIA) {
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
//                GlideApp.with(this)
//                    .load(BuildConfig.BASE_IMG_AVATAR + path)
//                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
//                    .into(binding.profileAvatar)
            }
        })

        binding.radioGender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioMale) {
                viewModel.setGender(PRIA)
            } else {
                viewModel.setGender(WANITA)
            }
        }

        viewModel.status.observe(this, Observer {
            if (it) {
                val intent = Intent()
                intent.putExtra(EXTRA_MESSAGE, getString(R.string.update_profile_success))
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

        viewModel.department.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.department.setText(it)
            }
        })

        binding.btnUpdate.setOnClickListener {
            when {
                binding.firstName.text.toString().isEmpty() -> binding.firstName.error =
                    getString(R.string.empty_field)
                binding.lastName.text.toString().isEmpty() -> binding.lastName.error =
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
        if (requestCode == REQUEST_DEPARTMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val department = data.getStringExtra(EXTRA_DEPARTMENT)
                    viewModel.setDepartment(department!!)
                }
            }
        } else if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
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
            } else -> super.onOptionsItemSelected(item)
        }
    }

}
