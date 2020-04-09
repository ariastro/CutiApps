package com.astronout.tmc.modules.leavetype

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityLeaveTypeBinding
import com.astronout.tmc.modules.leavetype.annual.view.GetAnnualActivity
import com.astronout.tmc.modules.leavetype.nonannual.view.GetNonAnnualActivity
import com.astronout.tmc.utils.glide.GlideApp
import com.bumptech.glide.GenericTransitionOptions

class LeaveTypeActivity : BaseActivity() {

    private lateinit var binding: ActivityLeaveTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_leave_type)

        setSupportActionBar(binding.toolbar)

        binding.annual.setOnClickListener {
            startActivity(Intent(this, GetAnnualActivity::class.java))
        }

        binding.nonAnnual.setOnClickListener {
            startActivity(Intent(this, GetNonAnnualActivity::class.java))
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

}
