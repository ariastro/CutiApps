package com.astronout.tmc.modules.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityMainBinding
import com.astronout.tmc.modules.auth.empolyees.view.LoginActivity
import com.astronout.tmc.modules.leaves.view.LeaveActivity
import com.astronout.tmc.modules.profile.view.ProfileActivity
import com.astronout.tmc.modules.requestleave.view.AnnualLeaveActivity
import com.astronout.tmc.modules.requestleave.view.AnnualLeaveActivity.Companion.REQUEST_ANNUAL_LEAVE
import com.astronout.tmc.modules.requestleave.view.ChooseLeaveTypeActivity
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import com.astronout.tmc.utils.dateFormat
import com.astronout.tmc.utils.glide.GlideApp
import com.astronout.tmc.utils.showToast
import com.bumptech.glide.GenericTransitionOptions
import org.joda.time.DateTime

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.main = viewModel

        setSupportActionBar(binding.toolbar)

        val dateNow = DateTime.now()
        binding.dateNow.text = dateNow.toString(dateFormat)

        binding.riwayatCuti.setOnClickListener {
            startActivity(Intent(this, LeaveActivity::class.java))
        }

        binding.profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.ajukanCuti.setOnClickListener {
            startActivityForResult(Intent(this, AnnualLeaveActivity::class.java), REQUEST_ANNUAL_LEAVE)
        }

        binding.logout.setOnClickListener {
            setLogoutDialog()
//            val alert = AlertDialog.Builder(this)
//            alert.setMessage(getString(R.string.exit_dialogue))
//            alert.setPositiveButton(android.R.string.yes) { _, _ ->
//                viewModel.clearData()
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            }
//            alert.setNegativeButton(android.R.string.no) { dialog, _ ->
//                dialog.cancel()
//            }
//            alert.show()
        }

        GlideApp.with(this)
            .load(R.drawable.bnnbanner)
            .into(binding.imgHome)

        GlideApp.with(this)
            .load(R.drawable.riwayat)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgRiwayatCuti)

        GlideApp.with(this)
            .load(R.drawable.exit)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgLogout)

        GlideApp.with(this)
            .load(R.drawable.ajukan)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgAjukanCuti)

        GlideApp.with(this)
            .load(R.drawable.profile)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgProfile)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ANNUAL_LEAVE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    showToast(data.getStringExtra(EXTRA_MESSAGE)!!)
                }
            }
        }
    }

}
