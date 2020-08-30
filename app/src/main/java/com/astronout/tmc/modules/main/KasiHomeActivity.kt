package com.astronout.tmc.modules.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityKasiHomeBinding
import com.astronout.tmc.modules.auth.local.User
import com.astronout.tmc.modules.kasi.view.ProfileKasiActivity
import com.astronout.tmc.modules.leaves.view.AccLeavesActivity
import com.astronout.tmc.modules.leaves.view.AllLeavesActivity
import com.astronout.tmc.utils.Constants.USER_KASI
import com.astronout.tmc.utils.Constants.USER_KSB
import com.astronout.tmc.utils.dateFormat
import com.astronout.tmc.utils.glide.GlideApp
import com.bumptech.glide.GenericTransitionOptions
import org.joda.time.DateTime

class KasiHomeActivity : BaseActivity() {

    private lateinit var binding: ActivityKasiHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_kasi_home)

        setSupportActionBar(binding.toolbar)
        if (User.userType == USER_KASI) {
            supportActionBar?.title = getString(R.string.halaman_kasi)
        } else if (User.userType == USER_KSB) {
            supportActionBar?.title = getString(R.string.halaman_ksb)
        }

        val dateNow = DateTime.now()
        binding.dateNow.text = dateNow.toString(dateFormat)

        GlideApp.with(this)
            .load(R.drawable.bnnbanner)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgHome)

        GlideApp.with(this)
            .load(R.drawable.nonannual)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgPermohonanCuti)

        GlideApp.with(this)
            .load(R.drawable.profile)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgProfile)

        GlideApp.with(this)
            .load(R.drawable.annual)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgSetujuiCuti)

        binding.cutiKaryawan.setOnClickListener {
            startActivity(Intent(this, AllLeavesActivity::class.java))
        }

        binding.setujuiCuti.setOnClickListener {
            startActivity(Intent(this, AccLeavesActivity::class.java))
        }

        binding.profile.setOnClickListener {
            startActivity(Intent(this, ProfileKasiActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.admin_home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            setLogoutDialog()
        }
        return super.onOptionsItemSelected(item)
    }

}