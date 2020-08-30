package com.astronout.tmc.modules.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityManagerHomeBinding
import com.astronout.tmc.modules.leaves.view.AccLeavesActivity
import com.astronout.tmc.modules.leaves.view.AllLeavesActivity
import com.astronout.tmc.modules.manager.view.ProfileManagerActivity
import com.astronout.tmc.utils.dateFormat
import com.astronout.tmc.utils.glide.GlideApp
import com.bumptech.glide.GenericTransitionOptions
import org.joda.time.DateTime
import java.time.LocalDate

class ManagerHomeActivity : BaseActivity() {

    private lateinit var binding: ActivityManagerHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_manager_home)

        setSupportActionBar(binding.toolbar)

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

        binding.profile.setOnClickListener {
            startActivity(Intent(this, ProfileManagerActivity::class.java))
        }

        binding.setujuiCuti.setOnClickListener {
            startActivity(Intent(this, AccLeavesActivity::class.java))
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