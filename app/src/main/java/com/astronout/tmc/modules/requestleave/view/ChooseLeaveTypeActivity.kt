package com.astronout.tmc.modules.requestleave.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityChooseLeaveTypeBinding
import com.astronout.tmc.modules.requestleave.view.AnnualLeaveActivity.Companion.REQUEST_ANNUAL_LEAVE
import com.astronout.tmc.modules.requestleave.view.NonAnnualLeaveActivity.Companion.REQUEST_NON_ANNUAL_LEAVE
import com.astronout.tmc.utils.Constants.EXTRA_MESSAGE
import com.astronout.tmc.utils.glide.GlideApp
import com.astronout.tmc.utils.showToast
import com.bumptech.glide.GenericTransitionOptions

class ChooseLeaveTypeActivity : BaseActivity() {

    private lateinit var binding: ActivityChooseLeaveTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_leave_type)

        setSupportActionBar(binding.toolbar)

        binding.annual.setOnClickListener {
            startActivityForResult(Intent(this, AnnualLeaveActivity::class.java), REQUEST_ANNUAL_LEAVE)
        }

        binding.nonAnnual.setOnClickListener {
            startActivityForResult(Intent(this, NonAnnualLeaveActivity::class.java), REQUEST_NON_ANNUAL_LEAVE)
        }

        GlideApp.with(this)
            .load(R.drawable.annual)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgAnnual)

        GlideApp.with(this)
            .load(R.drawable.nonannual)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgNonAnnual)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ANNUAL_LEAVE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val message = data.getStringExtra(EXTRA_MESSAGE)
                    showToast(message!!)
                }
            }
        } else if (requestCode == REQUEST_NON_ANNUAL_LEAVE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val message = data.getStringExtra(EXTRA_MESSAGE)
                    showToast(message!!)
                }
            }
        }
    }

}
