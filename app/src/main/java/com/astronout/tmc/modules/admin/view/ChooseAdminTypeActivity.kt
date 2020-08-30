package com.astronout.tmc.modules.admin.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityChooseAdminTypeBinding
import com.astronout.tmc.modules.kasi.view.KasiListActivity
import com.astronout.tmc.modules.manager.view.ManagerListActivity
import com.astronout.tmc.utils.glide.GlideApp
import com.bumptech.glide.GenericTransitionOptions

class ChooseAdminTypeActivity : BaseActivity() {

    private lateinit var binding: ActivityChooseAdminTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_admin_type)

        setSupportActionBar(binding.toolbar)

        binding.admin.setOnClickListener {
            startActivity(Intent(this, AdminListActivity::class.java))
        }

        binding.manager.setOnClickListener {
            startActivity(Intent(this, ManagerListActivity::class.java))
        }

        binding.kasi.setOnClickListener {
            startActivity(Intent(this, KasiListActivity::class.java))
        }

        GlideApp.with(this)
            .load(R.drawable.admin)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgAdmin)

        GlideApp.with(this)
            .load(R.drawable.manager)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgManager)

        GlideApp.with(this)
            .load(R.drawable.riwayat)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgKasi)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}