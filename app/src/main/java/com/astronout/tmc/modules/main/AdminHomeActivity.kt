package com.astronout.tmc.modules.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.astronout.tmc.R
import com.astronout.tmc.base.baseview.BaseActivity
import com.astronout.tmc.databinding.ActivityAdminHomeBinding
import com.astronout.tmc.modules.admin.view.ChooseAdminTypeActivity
import com.astronout.tmc.modules.admin.view.ProfileAdminActivity
import com.astronout.tmc.modules.department.view.DepartmentActivity
import com.astronout.tmc.modules.employees.view.EmployeesActivity
import com.astronout.tmc.modules.leaves.view.AllLeavesActivity
import com.astronout.tmc.modules.leavetype.LeaveTypeActivity
import com.astronout.tmc.modules.leavetype.annual.view.GetAnnualActivity
import com.astronout.tmc.utils.dateFormat
import com.astronout.tmc.utils.glide.GlideApp
import com.bumptech.glide.GenericTransitionOptions
import org.joda.time.DateTime

class AdminHomeActivity : BaseActivity() {

    private lateinit var binding: ActivityAdminHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_home)

        setSupportActionBar(binding.toolbar)

        val dateNow = DateTime.now()
        binding.dateNow.text = dateNow.toString(dateFormat)

        GlideApp.with(this)
            .load(R.drawable.bnnbanner)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgHome)

        GlideApp.with(this)
            .load(R.drawable.riwayat)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgDivisi)

        GlideApp.with(this)
            .load(R.drawable.nonannual)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgPermohonanCuti)

        GlideApp.with(this)
            .load(R.drawable.karyawan)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgKaryawan)

        GlideApp.with(this)
            .load(R.drawable.profile)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgProfile)

        GlideApp.with(this)
            .load(R.drawable.admin)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgAdmin)

        GlideApp.with(this)
            .load(R.drawable.annual)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.imgJenisCuti)

        binding.cutiKaryawan.setOnClickListener {
            startActivity(Intent(this, AllLeavesActivity::class.java))
        }

        binding.divisi.setOnClickListener {
            startActivity(Intent(this, DepartmentActivity::class.java))
        }

        binding.jenisCuti.setOnClickListener {
            startActivity(Intent(this, GetAnnualActivity::class.java))
        }

        binding.karyawan.setOnClickListener {
            startActivity(Intent(this, EmployeesActivity::class.java))
        }

        binding.profile.setOnClickListener {
            startActivity(Intent(this, ProfileAdminActivity::class.java))
        }

        binding.admin.setOnClickListener {
            startActivity(Intent(this, ChooseAdminTypeActivity::class.java))
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
